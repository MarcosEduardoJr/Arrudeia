package com.arrudeia.feature.social.data.chat

import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRegister
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRow
import com.arrudeia.feature.social.domain.chat_list.model.User
import kotlinx.coroutines.flow.Flow

interface SocialChatListRepository {

    suspend fun getUserImage(uuid: String): String
    suspend fun loadAcceptedFriendRequestListFromFirebase(): Flow<Result<List<FriendListRow>?>>
    suspend fun loadPendingFriendRequestListFromFirebase(): Flow<Result<List<FriendListRegister>>>

    suspend fun searchUserFromFirebase(userEmail: String): Flow<Result<User?>>

    suspend fun checkChatRoomExistedFromFirebase(acceptorUUID: String): Flow<Result<String>>
    suspend fun createChatRoomToFirebase(acceptorUUID: String): Flow<Result<String>>

    suspend fun checkFriendListRegisterIsExistedFromFirebase(
        acceptorEmail: String,
        acceptorUUID: String,
    ): Flow<Result<FriendListRegister?>>

    suspend fun createFriendListRegisterToFirebase(
        chatRoomUUID: String,
        acceptorEmail: String,
        acceptorUUID: String,
        acceptorName : String,
        requesterName : String
    ): Flow<Result<Boolean>>

    suspend fun acceptPendingFriendRequestToFirebase(registerUUID: String): Flow<Result<Boolean>>
    suspend fun rejectPendingFriendRequestToFirebase(registerUUID: String): Flow<Result<Boolean>>
    suspend fun openBlockedFriendToFirebase(registerUUID: String): Flow<Result<Boolean>>
}