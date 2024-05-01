import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.sign.domain.CreateUserDataStoreUseCase
import com.arrudeia.feature.sign.domain.CreateUserFirebaseUseCase
import com.arrudeia.feature.sign.domain.SignInUserFirebaseUseCase
import com.arrudeia.feature.sign.domain.entity.SignFirebaseUserUseCaseEntity
import com.arrudeia.feature.sign.presentation.viewmodel.SignViewModel
import com.arrudeia.feature.sign.presentation.viewmodel.SignViewModel.SignUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SignViewModelTest : ViewModelTest() {

    @Mock
    private lateinit var createUserUseCase: CreateUserFirebaseUseCase

    @Mock
    private lateinit var createUserDataStoreUseCase: CreateUserDataStoreUseCase

    @Mock
    private lateinit var signUseCase: SignInUserFirebaseUseCase

    private lateinit var viewModel: SignViewModel

    @Before
    fun setUp() {
        viewModel = SignViewModel(createUserUseCase, createUserDataStoreUseCase, signUseCase)
    }

    @Test
    fun `test signUp with valid credentials`() = runBlockingTest {
        val email = "test@test.com"
        val password = "password"
        val user = SignFirebaseUserUseCaseEntity("1", "Test User", email)

        `when`(createUserUseCase.invoke(email, password)).thenReturn(user)
        `when`(createUserDataStoreUseCase.invoke(user.uid, user.name, user.email)).thenReturn(true)

        viewModel.signUp(email, password)

        Assert.assertTrue(viewModel.uiState.value is SignUiState.Success)
    }

    @Test
    fun `test signUp with invalid credentials`() = runBlockingTest {
        val email = "test@test.com"
        val password = "password"

        `when`(createUserUseCase.invoke(email, password)).thenReturn(null)

        viewModel.signUp(email, password)

        Assert.assertTrue(viewModel.uiState.value is SignUiState.Error)
    }

    @Test
    fun `test signIn with valid credentials`() = runBlockingTest {
        val email = "test@test.com"
        val password = "password"
        val user = SignFirebaseUserUseCaseEntity("1", "Test User", email)

        `when`(signUseCase.invoke(email, password)).thenReturn(user)
        `when`(createUserDataStoreUseCase.invoke(user.uid, user.name, user.email)).thenReturn(true)

        viewModel.signIn(email, password)

        Assert.assertTrue(viewModel.uiState.value is SignUiState.Success)
    }

    @Test
    fun `test signIn with invalid credentials`() = runBlockingTest {
        val email = "test@test.com"
        val password = "password"

        `when`(signUseCase.invoke(email, password)).thenReturn(null)

        viewModel.signIn(email, password)

        Assert.assertTrue(viewModel.uiState.value is SignUiState.Error)
    }
}