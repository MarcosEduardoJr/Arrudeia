package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.AcceptPendingFriendRequestToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.CheckChatRoomExistedFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.CheckFriendListRegisterIsExistedFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.CreateChatRoomToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.CreateFriendListRegisterToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.LoadAcceptedFriendRequestListFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.LoadPendingFriendRequestListFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.OpenBlockedFriendToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.RejectPendingFriendRequestToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.SearchUserFromFirebase

data class UserListScreenUseCases(
    val acceptPendingFriendRequestToFirebase: AcceptPendingFriendRequestToFirebase,
    val checkChatRoomExistedFromFirebase: CheckChatRoomExistedFromFirebase,
    val checkFriendListRegisterIsExistedFromFirebase: CheckFriendListRegisterIsExistedFromFirebase,
    val createChatRoomToFirebase: CreateChatRoomToFirebase,
    val createFriendListRegisterToFirebase: CreateFriendListRegisterToFirebase,
    val loadAcceptedFriendRequestListFromFirebase: LoadAcceptedFriendRequestListFromFirebase,
    val loadPendingFriendRequestListFromFirebase: LoadPendingFriendRequestListFromFirebase,
    val openBlockedFriendToFirebase: OpenBlockedFriendToFirebase,
    val rejectPendingFriendRequestToFirebase: RejectPendingFriendRequestToFirebase,
    val searchUserFromFirebase: SearchUserFromFirebase,
)