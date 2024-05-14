import androidx.lifecycle.viewModelScope
import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.domain.GetUserAddressUseCase
import com.arrudeia.feature.profile.domain.UpdateUserAddressUseCase
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel.AddressUiState
import com.arrudeia.feature.profile.presentation.viewmodel.ProfileAddressViewModel.PersonalInformationUpdateUserUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class ProfileAddressViewModelTest : ViewModelTest() {

    @Mock
    private var getUserAddressUseCase = Mockito.mock(GetUserAddressUseCase::class.java)

    @Mock
    private var updateUserAddressUseCase = Mockito.mock(UpdateUserAddressUseCase::class.java)

    private lateinit var viewModel: ProfileAddressViewModel

    @Before
    fun setUp() {
        viewModel = ProfileAddressViewModel(getUserAddressUseCase, updateUserAddressUseCase)
    }


    @After
    fun `after each test`() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun `test getUserPersonalInformation with valid data`() = coTest {
        val user = UserAddressUseCaseEntity(
            "1",
            "12345",
            "Street",
            123,
            "District",
            "City",
            "State",
            "Country"
        )

        `when`(getUserAddressUseCase.invoke()).thenReturn(Result.Success(user))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.uiState.value is AddressUiState.Success)
    }

    @Test
    fun `test getUserPersonalInformation with error`() = coTest {
        `when`(getUserAddressUseCase.invoke()).thenReturn(Result.Error(null))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.uiState.value is AddressUiState.Error)
    }

    @Test
    fun `test saveAddress with valid data`() = coTest {
        val user = UserAddressUseCaseEntity(
            "1",
            "12345",
            "Street",
            123,
            "District",
            "City",
            "State",
            "Country"
        )
        viewModel.uuidCurrentUser = "1"
        `when`(updateUserAddressUseCase(user)).thenReturn(Result.Success(any()))

        viewModel.saveAddress("12345", "Street", 123, "District", "City", "State", "Country")

        Assert.assertTrue(viewModel.uiStateUpdateUser.value is PersonalInformationUpdateUserUiState.Success)
    }

    @Test
    fun `test saveAddress with error`() = coTest {
        val user = UserAddressUseCaseEntity(
            "1",
            "12345",
            "Street",
            123,
            "District",
            "City",
            "State",
            "Country"
        )

        `when`(updateUserAddressUseCase(user)).thenReturn(Result.Error(R.string.error_update_user))

        viewModel.saveAddress("12345", "Street", 123, "District", "City", "State", "Country")

        Assert.assertTrue(viewModel.uiStateUpdateUser.value is PersonalInformationUpdateUserUiState.Error)
    }
}