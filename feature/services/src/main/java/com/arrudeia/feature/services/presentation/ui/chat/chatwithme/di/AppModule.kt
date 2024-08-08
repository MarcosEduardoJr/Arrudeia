package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.di

import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.data.repository.AuthScreenRepositoryImpl
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.data.repository.ChatScreenRepositoryImpl
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.data.repository.ProfileScreenRepositoryImpl
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.data.repository.UserListScreenRepositoryImpl
import com.example.chatwithme.domain.repository.AuthScreenRepository
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.repository.ChatScreenRepository
import com.example.chatwithme.domain.repository.ProfileScreenRepository
import com.example.chatwithme.domain.repository.UserListScreenRepository
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen.AuthUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen.IsUserAuthenticatedInFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen.SignIn
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.authScreen.SignUp
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen.BlockFriendToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen.ChatScreenUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen.InsertMessageToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen.LoadMessageFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.chatScreen.LoadOpponentProfileFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.CreateOrUpdateProfileToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.LoadProfileFromFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.ProfileScreenUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.SetUserStatusToFirebase
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.SignOut
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.UploadPictureToFirebase
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
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.userListScreen.UserListScreenUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
 //   @Provides
   // fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    fun provideFirebaseDatabaseInstance() = FirebaseDatabase.getInstance()

//    @Provides
//    fun provideSharedPreferences(application: Application) =
//        application.getSharedPreferences("login", Context.MODE_PRIVATE)


    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
    ): AuthScreenRepository = AuthScreenRepositoryImpl(auth)

    @Provides
    fun provideChatScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): ChatScreenRepository = ChatScreenRepositoryImpl(auth, database)

    @Provides
    fun provideProfileScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase,
        storage: FirebaseStorage
    ): ProfileScreenRepository = ProfileScreenRepositoryImpl(auth, database, storage)

    @Provides
    fun provideUserListScreenRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): UserListScreenRepository = UserListScreenRepositoryImpl(auth, database)

    @Provides
    fun provideAuthScreenUseCase(authRepository: AuthScreenRepository) = AuthUseCases(
        isUserAuthenticated = IsUserAuthenticatedInFirebase(authRepository),
        signIn = SignIn(authRepository),
        signUp = SignUp(authRepository)
    )

    @Provides
    fun provideChatScreenUseCase(chatScreenRepository: ChatScreenRepository) = ChatScreenUseCases(
        blockFriendToFirebase = BlockFriendToFirebase(chatScreenRepository),
        insertMessageToFirebase = InsertMessageToFirebase(chatScreenRepository),
        loadMessageFromFirebase = LoadMessageFromFirebase(chatScreenRepository),
        opponentProfileFromFirebase = LoadOpponentProfileFromFirebase(chatScreenRepository)
    )

    @Provides
    fun provideProfileScreenUseCase(profileScreenRepository: ProfileScreenRepository) =
        ProfileScreenUseCases(
            createOrUpdateProfileToFirebase = CreateOrUpdateProfileToFirebase(
                profileScreenRepository
            ),
            loadProfileFromFirebase = LoadProfileFromFirebase(profileScreenRepository),
            setUserStatusToFirebase = SetUserStatusToFirebase(profileScreenRepository),
            signOut = SignOut(profileScreenRepository),
            uploadPictureToFirebase = UploadPictureToFirebase(profileScreenRepository)
        )

    @Provides
    fun provideUserListScreenUseCase(userListScreenRepository: UserListScreenRepository) =
        UserListScreenUseCases(
            acceptPendingFriendRequestToFirebase = AcceptPendingFriendRequestToFirebase(
                userListScreenRepository
            ),
            checkChatRoomExistedFromFirebase = CheckChatRoomExistedFromFirebase(
                userListScreenRepository
            ),
            checkFriendListRegisterIsExistedFromFirebase = CheckFriendListRegisterIsExistedFromFirebase(
                userListScreenRepository
            ),
            createChatRoomToFirebase = CreateChatRoomToFirebase(userListScreenRepository),
            createFriendListRegisterToFirebase = CreateFriendListRegisterToFirebase(
                userListScreenRepository
            ),
            loadAcceptedFriendRequestListFromFirebase = LoadAcceptedFriendRequestListFromFirebase(
                userListScreenRepository
            ),
            loadPendingFriendRequestListFromFirebase = LoadPendingFriendRequestListFromFirebase(
                userListScreenRepository
            ),
            openBlockedFriendToFirebase = OpenBlockedFriendToFirebase(userListScreenRepository),
            rejectPendingFriendRequestToFirebase = RejectPendingFriendRequestToFirebase(
                userListScreenRepository
            ),
            searchUserFromFirebase = SearchUserFromFirebase(userListScreenRepository),
        )
}