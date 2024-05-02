import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.profile.domain.UpdateUserPersonalInformationUseCase
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.profile.presentation.viewmodel.ProfilePersonalInformationViewModel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfilePersonalInformationViewModel.PersonalInformationUiState
import com.arrudeia.feature.profile.presentation.viewmodel.ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class ProfilePersonalInformationViewModelTest : ViewModelTest() {

    @Mock
    private lateinit var getUserPersonalInformationUseCase: GetUserPersonalInformationUseCase

    @Mock
    private lateinit var updateUserPersonalInformationUseCase: UpdateUserPersonalInformationUseCase

    private lateinit var viewModel: ProfilePersonalInformationViewModel

    @Before
    fun setUp() {
        viewModel = ProfilePersonalInformationViewModel(
            getUserPersonalInformationUseCase,
            updateUserPersonalInformationUseCase
        )
    }

    @Test
    fun `test getUserPersonalInformation with valid data`() = coTest {
        val user = UserPersonalInformationUseCaseEntity(
            "1",
            "Test User",
            "test@test.com",
            "123456789",
            "123456789",
            "01/01/2000",
            "image"
        )

        `when`(getUserPersonalInformationUseCase.invoke()).thenReturn(Result.Success(user))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.uiState.value is PersonalInformationUiState.Success)
    }

    @Test
    fun `test getUserPersonalInformation with error`() = coTest {
        `when`(getUserPersonalInformationUseCase()).thenReturn(Result.Error(R.string.error_get_user))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.uiState.value is PersonalInformationUiState.Error)
    }


    @Test
    fun `test savePersonalInformation with error`() = coTest {
        val user = UserPersonalInformationUseCaseEntity(
            "1",
            "Test User",
            "test@test.com",
            "123456789",
            "123456789",
            "01/01/2000",
            "image"
        )
        `when`(
            updateUserPersonalInformationUseCase.invoke(
                user,
                null
            )
        ).thenReturn(Result.Error(R.string.error_update_user))

        viewModel.savePersonalInformation(
            "Test User",
            "123456789",
            "test@test.com",
            "123456789",
            "01/01/2000",
        )

        Assert.assertTrue(viewModel.uiStateUpdateUser.value is PersonalInformationUpdateUserUiState.Error)
    }
}