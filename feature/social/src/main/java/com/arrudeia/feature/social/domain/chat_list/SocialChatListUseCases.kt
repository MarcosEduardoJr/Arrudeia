package com.arrudeia.feature.social.domain.chat_list

data class SocialChatListUseCases(
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