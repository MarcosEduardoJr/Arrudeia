import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.profile.domain.LogoutCurrentUserDataStoreUseCase
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileViewModel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileViewModel.ProfileUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest : ViewModelTest() {

    @Mock
    private lateinit var getUserPersonalInformationUseCase: GetUserPersonalInformationUseCase

    @Mock
    private lateinit var logoutCurrentUserDataStoreUseCase: LogoutCurrentUserDataStoreUseCase

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        viewModel =
            ProfileViewModel(getUserPersonalInformationUseCase, logoutCurrentUserDataStoreUseCase)
    }

    @Test
    fun `test getUserPersonalInformation with valid data`() = coTest {
        val user = UserPersonalInformationUseCaseEntity("Test User", "test@test.com", "image")

        `when`(getUserPersonalInformationUseCase.invoke()).thenReturn(Result.Success(user))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.uiState.value is ProfileUiState.Success)
    }

    @Test
    fun `test getUserPersonalInformation with error`() = coTest {
        `when`(getUserPersonalInformationUseCase()).thenReturn(Result.Error(R.string.error_get_user))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.uiState.value is ProfileUiState.Error)
    }
}