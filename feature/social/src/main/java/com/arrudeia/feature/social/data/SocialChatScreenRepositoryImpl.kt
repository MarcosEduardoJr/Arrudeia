package com.arrudeia.feature.social.data

import com.arrudeia.core.result.Result
import com.arrudeia.core.utils.Constants.ERROR_MESSAGE
import com.arrudeia.feature.social.domain.chat_list.model.ChatMessage
import com.arrudeia.feature.social.domain.chat_list.model.FriendStatus
import com.arrudeia.feature.social.domain.chat_list.model.MessageStatus
import com.arrudeia.feature.social.domain.chat_list.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialChatScreenRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : SocialChatScreenRepository {
    override suspend fun insertMessageToFirebase(
        chatRoomUUID: String,
        messageContent: String,
        registerUUID: String,
    ): Flow<Result<Boolean>> = flow {
        try {
            emit(Result.Loading)
            val userUUID = auth.currentUser?.uid
            val userEmail = auth.currentUser?.email
            val messageUUID = UUID.randomUUID().toString()

            val message = ChatMessage(
                userUUID!!,
                messageContent,
                System.currentTimeMillis(),
                MessageStatus.RECEIVED.toString()
            )

            val databaseRefForLastMessage =
                database.reference.child("Traveler_List").child(registerUUID)
                    .child("lastMessage")
            databaseRefForLastMessage.setValue(message).await() // for last message

            val databaseRefForChatMessage =
                database.reference.child("Social_Chat_Rooms").child(chatRoomUUID)
                    .child(messageUUID)
            databaseRefForChatMessage.setValue(message).await() // for insert message

            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadMessagesFromFirebase(
        chatRoomUUID: String,
        opponentUUID: String,
        registerUUID: String
    ): Flow<Result<List<ChatMessage>>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Result.Loading)
            val userUUID = auth.currentUser?.uid

            val databaseRefForMessageStatus =
                database.getReference("Traveler_List").child(registerUUID)
                    .child("lastMessage")
            val lastMessageProfileUUID =
                databaseRefForMessageStatus.child("profileUUID").get().await().value as String

            if (lastMessageProfileUUID != userUUID) {
                databaseRefForMessageStatus.child("status").setValue(MessageStatus.READ.toString())
            }
            val databaseRefForLoadMessages =
                database.getReference("Social_Chat_Rooms").child(chatRoomUUID)

            val postListener =
                databaseRefForLoadMessages.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val messageList = arrayListOf<ChatMessage>()
                        var unReadMessageKeys = listOf<String>()

                        val job2 = launch {
                            snapshot.children.forEach {
                                if (it.value?.javaClass != Boolean::class.java) {
                                    val chatMessage = it.getValue(ChatMessage::class.java)
                                    if (chatMessage != null) {
                                        messageList.add(chatMessage)

                                        if (chatMessage.profileUUID != userUUID && chatMessage.status == MessageStatus.RECEIVED.toString()) {
                                            unReadMessageKeys =
                                                unReadMessageKeys + it.key.toString()
                                        }
                                    }
                                }
                            }
                            messageList.sortBy { it.date }
                            this@callbackFlow.trySendBlocking(Result.Success(messageList))
                        }
                        job2.invokeOnCompletion {
                            for (i in unReadMessageKeys) {
                                databaseRefForLoadMessages.child(i).updateChildren(
                                    mapOf(
                                        Pair(
                                            "/status/",
                                            MessageStatus.READ
                                        )
                                    )
                                )
                            }
                        }
                        messageList.clear()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Result.ErrorMessage(error.message))
                    }
                })
            databaseRefForLoadMessages.addValueEventListener(postListener)

            awaitClose {
                databaseRefForLoadMessages.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun loadOpponentProfileFromFirebase(opponentUUID: String): Flow<Result<User>> =
        callbackFlow {
            try {

                this@callbackFlow.trySendBlocking(Result.Loading)

                val databaseReference =
                    database.getReference("Traveler_Profiles").child(opponentUUID).child("profile")

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        this@callbackFlow.trySendBlocking(Result.Success(user!!))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Result.ErrorMessage(error.message))
                    }
                })
            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
            }

            awaitClose {
                channel.close()
                cancel()
            }
        }

    override suspend fun blockFriendToFirebase(registerUUID: String): Flow<Result<Boolean>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Result.Loading)

                val myUUID = auth.currentUser?.uid

                val databaseReference =
                    database.getReference("Traveler_List").child(registerUUID)

                val childUpdates = mutableMapOf<String, Any>()
                childUpdates["/status/"] = FriendStatus.BLOCKED.toString()
                childUpdates["/blockedby/"] = myUUID.toString()

                databaseReference.updateChildren(childUpdates).await()

                this@callbackFlow.trySendBlocking(Result.Success(true))

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
            }

            awaitClose {
                channel.close()
                cancel()
            }
        }
}