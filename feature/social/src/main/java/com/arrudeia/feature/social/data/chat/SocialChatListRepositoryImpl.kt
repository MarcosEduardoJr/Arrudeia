package com.arrudeia.feature.social.data.chat

import com.arrudeia.core.data.repository.FirebaseUserRepositoryImpl
import com.arrudeia.core.result.Result
import com.arrudeia.core.utils.Constants.ERROR_MESSAGE
import com.arrudeia.core.utils.Constants.NO_CHATROOM_IN_FIREBASE_DATABASE
import com.arrudeia.feature.social.domain.chat_list.model.ChatMessage
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRegister
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRow
import com.arrudeia.feature.social.domain.chat_list.model.FriendStatus
import com.arrudeia.feature.social.domain.chat_list.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class SocialChatListRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
) : SocialChatListRepository {

    override suspend fun getUserImage(uuid: String): String {
        val storageRef = Firebase.storage.reference
        val imagesRef =
            storageRef.child(FirebaseUserRepositoryImpl.FOLDER_IMAGE_USER + "/$uuid.jpg")
        return suspendCancellableCoroutine { continuation ->
            imagesRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(it.result.toString())
                } else continuation.resume("")
            }
        }
    }

    suspend fun createOrUpdateProfileImageToFirebaseDatabaseChat(
        imgUrl: String,
        userUUID: String,
        userEmail: String,
        userName: String
    ) {
        val databaseReference =
            database.getReference("Traveler_Profiles").child(userUUID).child("profile")
        val childUpdates = mutableMapOf<String, Any>()
        childUpdates["/profileUUID/"] = userUUID
        if (userEmail.isNotEmpty()) childUpdates["/userEmail/"] = userEmail
        if (imgUrl.isNotEmpty()) childUpdates["/userProfilePictureUrl/"] =
            imgUrl
        if (userName.isNotEmpty()) childUpdates["/userName/"] = userName

        databaseReference.updateChildren(childUpdates).await()
    }

    override suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Result<List<FriendListRow>?>> =
        callbackFlow {
            try {
                val myUUID = auth.currentUser?.uid
                val databaseReference = database.getReference("Traveler_List")

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val friendListRegisterAcceptedList = mutableListOf<FriendListRegister>()
                        launch {
                            for (i in snapshot.children) {
                                val friendListRegister =
                                    i.getValue(FriendListRegister::class.java)

                                if (friendListRegister?.status == FriendStatus.ACCEPTED.toString()) {
                                    friendListRegisterAcceptedList += friendListRegister
                                }
                            }
                            launch {
                                val friendListUiRowList = mutableListOf<FriendListRow>()

                                for (i in friendListRegisterAcceptedList) {
                                    val pictureUrl = getUserImage(
                                        if (myUUID.contentEquals(i.acceptorUUID)) i.requesterUUID else i.acceptorUUID
                                    )
                                    val chatRoomUID: String = i.chatRoomUUID
                                    val registerUUID: String = i.registerUUID
                                    val lastMessage: ChatMessage = i.lastMessage

                                    var email: String = ""
                                    var uuid: String = ""
                                    var oneSignalUserId: String = ""

                                    if (i.requesterUUID == myUUID) {
                                        email = i.acceptorEmail
                                        uuid = i.acceptorUUID
                                    } else if (i.acceptorUUID == myUUID) {
                                        email = i.requesterEmail
                                        uuid = i.requesterUUID
                                    }

                                    if (email != "" && uuid != "") {
                                        val friendListRow = FriendListRow(
                                            chatRoomUID,
                                            email,
                                            uuid,
                                            oneSignalUserId,
                                            registerUUID,
                                            pictureUrl,
                                            lastMessage
                                        )
                                        friendListUiRowList += friendListRow
                                    }
                                }

                                var resultList: MutableList<FriendListRow>? = null

                                for (i in friendListUiRowList) {
                                    val friendListUiRow = FriendListRow(
                                        chatRoomUUID = i.chatRoomUUID,
                                        userEmail = i.userEmail,
                                        userUUID = i.userUUID,
                                        oneSignalUserId = i.oneSignalUserId,
                                        registerUUID = i.registerUUID,
                                        userPictureUrl = i.userPictureUrl,
                                        i.lastMessage,
                                    )
                                    if (resultList == null)
                                        resultList = mutableListOf<FriendListRow>()
                                    resultList?.add(friendListUiRow)
                                }



                                this@callbackFlow.trySendBlocking(
                                    Result.Success(
                                        resultList?.toList()
                                    )
                                )
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Result.ErrorMessage(error.message))
                    }
                })
                awaitClose {
                    channel.close()
                    cancel()
                }
            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(
                    Result.ErrorMessage(
                        e.message ?: ERROR_MESSAGE
                    )
                )
            }
        }

    override suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Result<List<FriendListRegister>>> =
        callbackFlow {
            val myUUID = auth.currentUser?.uid

            val databaseReference = database.getReference("Traveler_List")

            val postListener =
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        try {
                            var resultList = listOf<FriendListRegister>()
                            for (i in snapshot.children) {
                                val friendListRegister =
                                    i.getValue(FriendListRegister::class.java)
                                if (friendListRegister?.status == FriendStatus.PENDING.toString() && friendListRegister.acceptorUUID == myUUID) {
                                    resultList = resultList + friendListRegister
                                }
                            }
                            this@callbackFlow.trySendBlocking(Result.Success(resultList))
                        } catch (e: Exception) {
                            this@callbackFlow.trySendBlocking(
                                Result.ErrorMessage(
                                    e.message ?: ERROR_MESSAGE
                                )
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        this@callbackFlow.trySendBlocking(Result.ErrorMessage(error.message))
                    }
                })
            databaseReference.addValueEventListener(postListener)
            awaitClose {
                databaseReference.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        }

    override suspend fun searchUserFromFirebase(userEmail: String): Flow<Result<User?>> =
        callbackFlow {
            try {

                this@callbackFlow.trySendBlocking(Result.Loading)

                val databaseReference = database.getReference("Traveler_Profiles")

                var user: User?

                databaseReference.get().addOnSuccessListener {
                    var flagForControl = false

                    val myJob = launch {
                        for (i in it.children) {
                            user = i.child("profile").getValue(User::class.java)!!
                            if (user?.userEmail == userEmail) {
                                flagForControl = true
                                this@callbackFlow.trySendBlocking(Result.Success(user))
                            }
                        }
                    }

                    myJob.invokeOnCompletion {
                        if (!flagForControl) {
                            this@callbackFlow.trySendBlocking(Result.Success(null))
                        }
                    }

                }.addOnFailureListener {
                    this@callbackFlow.trySendBlocking(
                        Result.ErrorMessage(
                            it.message ?: ERROR_MESSAGE
                        )
                    )
                }

                awaitClose {
                    channel.close()
                    cancel()
                }

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(
                    Result.ErrorMessage(
                        e.message ?: ERROR_MESSAGE
                    )
                )
            }
        }

    override suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Result<String>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Result.Loading)

                val requesterUUID = auth.currentUser?.uid

                val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
                hashMapOfRequesterUUIDAndAcceptorUUID[requesterUUID!!] = acceptorUUID

                val hashMapOfAcceptorUUIDAndRequesterUUID = hashMapOf<String, String>()
                hashMapOfAcceptorUUIDAndRequesterUUID[acceptorUUID] = requesterUUID

                val gson = Gson()
                val requesterUUIDAndAcceptorUUID =
                    gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)
                val acceptorUUIDAndRequesterUUID =
                    gson.toJson(hashMapOfAcceptorUUIDAndRequesterUUID)

                val databaseReference = database.getReference("Social_Chat_Rooms")

                databaseReference.get().addOnSuccessListener {
                    try {
                        var keyListForControl = listOf<String>()
                        val hashMapForControl = hashMapOf<String, Any>()
                        for (i in it.children) {
                            val key = i.key as String
                            keyListForControl = keyListForControl + key
                            val hashMap: Map<String, Any> = Gson().fromJson(
                                i.key,
                                object : TypeToken<HashMap<String?, Any?>?>() {}.type
                            )

                            hashMapForControl.putAll(hashMap)
                        }

                        val chatRoomUUIDString: String?

                        if (keyListForControl.contains(requesterUUIDAndAcceptorUUID)) {

                            //ChatRoom opened by Requester
                            val hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase =
                                hashMapOf<String, Any>()
                            hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase[requesterUUID] =
                                acceptorUUID

                            val gson = Gson()
                            chatRoomUUIDString = gson.toJson(
                                hashMapOfRequesterUUIDAndAcceptorUUIDForSaveMessagesToFirebase
                            )

                            this@callbackFlow.trySendBlocking(Result.Success(chatRoomUUIDString!!))

                        } else if (keyListForControl.contains(acceptorUUIDAndRequesterUUID)) {

                            //ChatRoom opened by Acceptor
                            val hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase =
                                hashMapOf<String, Any>()
                            hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase[acceptorUUID] =
                                requesterUUID

                            val gson = Gson()
                            chatRoomUUIDString = gson.toJson(
                                hashMapOfAcceptorUUIDAndRequesterUUIDForSaveMessagesToFirebase
                            )

                            this@callbackFlow.trySendBlocking(Result.Success(chatRoomUUIDString!!))

                        } else {
                            this@callbackFlow.trySendBlocking(
                                Result.Success(
                                    NO_CHATROOM_IN_FIREBASE_DATABASE
                                )
                            )
                        }
                    } catch (e: JsonSyntaxException) {
                        this@callbackFlow.trySendBlocking(
                            Result.ErrorMessage(
                                e.message ?: ERROR_MESSAGE
                            )
                        )
                    }
                }

                awaitClose {
                    channel.close()
                    cancel()
                }

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(
                    Result.ErrorMessage(
                        e.message ?: ERROR_MESSAGE
                    )
                )
            }
        }

    override suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Result<String>> =
        flow {
            try {
                emit(Result.Loading)

                val requesterUUID = auth.currentUser?.uid

                val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
                hashMapOfRequesterUUIDAndAcceptorUUID[requesterUUID!!] = acceptorUUID

                val databaseReference = database.getReference("Social_Chat_Rooms")

                val gson = Gson()
                val requesterUUIDAndAcceptorUUID =
                    gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)

                databaseReference
                    .child(requesterUUIDAndAcceptorUUID)
                    .setValue(true)
                    .await()

                emit(Result.Success(requesterUUIDAndAcceptorUUID))

            } catch (e: Exception) {
                emit(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun checkFriendListRegisterIsExistedFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String
    ): Flow<Result<FriendListRegister?>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Result.Loading)
            val requesterUUID = auth.currentUser?.uid
            val requesterEmailUUID = auth.currentUser?.email
            val databaseReference = database.getReference("Traveler_List")

            databaseReference.get().addOnSuccessListener {
                var result: FriendListRegister? = null

                val job = launch {
                    for (i in it.children) {
                        val friendListRegister = i.getValue(FriendListRegister::class.java)
                        if (friendListRegister?.requesterUUID == requesterUUID && friendListRegister?.acceptorUUID == acceptorUUID) {
                            result = friendListRegister
                        } else if (friendListRegister?.requesterUUID == acceptorUUID && friendListRegister.acceptorUUID == requesterUUID) {
                            result = friendListRegister
                        }
                    }
                }




                job.invokeOnCompletion {
                    this@callbackFlow.trySendBlocking(Result.Success(result))
                }
            }


            awaitClose {
                channel.close()
                cancel()
            }

        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Result.ErrorMessage(e.message ?: "Error Message"))
        }
    }

    override suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorName : String,
        requesterName : String
    ): Flow<Result<Boolean>> = flow {
        try {
            emit(Result.Loading)

            val registerUUID = UUID.randomUUID().toString()

            val requesterEmail = auth.currentUser?.email
            val requesterUUID = auth.currentUser?.uid


            val databaseReference = database.getReference("Traveler_List")

            val friendListRegister =
                FriendListRegister(
                    chatRoomUUID,
                    registerUUID,
                    requesterEmail!!,
                    requesterUUID!!,
                    acceptorEmail,
                    acceptorUUID,
                    FriendStatus.ACCEPTED.toString(),
                    ChatMessage(),
                )

            databaseReference
                .child(registerUUID)
                .setValue(friendListRegister)
                .await()


            createOrUpdateProfileImageToFirebaseDatabaseChat(
                getUserImage(requesterUUID.orEmpty()),
                requesterUUID.orEmpty(),
                requesterEmail.orEmpty(),
                acceptorName
            )
            createOrUpdateProfileImageToFirebaseDatabaseChat(
                getUserImage(acceptorUUID),
                acceptorUUID,
                acceptorEmail,
                requesterName
            )

            emit(Result.Success(true))

        } catch (e: Exception) {
            emit(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Result<Boolean>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Result.Loading)

                val databaseReference =
                    database.getReference("Traveler_List").child(registerUUID)

                val childUpdates = mutableMapOf<String, Any>()
                childUpdates["/status/"] = FriendStatus.ACCEPTED.toString()

                databaseReference.updateChildren(childUpdates).addOnSuccessListener {
                    this@callbackFlow.trySendBlocking(Result.Success(true))
                }.addOnFailureListener {
                    this@callbackFlow.trySendBlocking(Result.Success(false))
                }

            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(
                    Result.ErrorMessage(
                        e.message ?: ERROR_MESSAGE
                    )
                )
            }

            awaitClose {
                channel.close()
                cancel()
            }
        }

    override suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Result<Boolean>> =
        flow {
            try {
                emit(Result.Loading)
                database.getReference("Traveler_List").child(registerUUID).setValue(null)
                    .await()
                emit(Result.Success(true))

            } catch (e: Exception) {
                emit(Result.ErrorMessage(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Result<Boolean>> =
        callbackFlow {
            try {
                this@callbackFlow.trySendBlocking(Result.Loading)

                val myUUID = auth.currentUser?.uid

                val databaseReference =
                    database.getReference("Friend_List").child(registerUUID)


                databaseReference.get().addOnSuccessListener {

                    val result = it.value as Map<*, *>

                    if (result["blockedby"] == myUUID) {
                        val childUpdates = mutableMapOf<String, Any?>()
                        childUpdates["/status/"] = FriendStatus.ACCEPTED.toString()
                        childUpdates["/blockedby/"] = null

                        databaseReference.updateChildren(childUpdates)

                        this@callbackFlow.trySendBlocking(Result.Success(true))
                    } else {
                        this@callbackFlow.trySendBlocking(Result.Success(false))
                    }
                }
            } catch (e: Exception) {
                this@callbackFlow.trySendBlocking(
                    Result.ErrorMessage(
                        e.message ?: ERROR_MESSAGE
                    )
                )
            }

            awaitClose {
                channel.close()
                cancel()
            }
        }
}