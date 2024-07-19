import com.arrudeia.core.places.domain.GetAllArrudeiaPlacesUseCase
import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.home.domain.GetAllArrudeiaTvUseCase
import com.arrudeia.feature.home.domain.GetAllStatesByCountryUseCase
import com.arrudeia.feature.home.domain.GetAllTravelHomeUseCase
import com.arrudeia.feature.home.domain.GetUserPersonalInformationUseCase
import com.arrudeia.feature.home.domain.entity.ArrudeiaUseCaseEntity
import com.arrudeia.feature.home.domain.entity.TravelUseCaseEntity
import com.arrudeia.feature.home.domain.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.home.presentation.viewmodel.ArrudeiaTvUiState
import com.arrudeia.feature.home.presentation.viewmodel.HomeViewModel
import com.arrudeia.feature.home.presentation.viewmodel.ProfileUiState
import com.arrudeia.feature.home.presentation.viewmodel.TravelUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : ViewModelTest() {

    @Mock
    private var getAllTravelHomeUseCase = Mockito.mock(
        GetAllTravelHomeUseCase::class.java
    )

    @Mock
    private var getAllArrudeiaTvUseCase = Mockito.mock(GetAllArrudeiaTvUseCase::class.java)

    @Mock
    private lateinit var getUserPersonalInformationUseCase: GetUserPersonalInformationUseCase

    @Mock
    private lateinit var statesByCountryUseCase: GetAllStatesByCountryUseCase
    @Mock
    private lateinit var getAllArrudeiaPlacesUseCase: GetAllArrudeiaPlacesUseCase


    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            getAllTravelHomeUseCase,
            getAllArrudeiaTvUseCase,
            getUserPersonalInformationUseCase,
            statesByCountryUseCase,
            getAllArrudeiaPlacesUseCase
        )
    }

    @Test
    fun `test fetchDataArrTv with valid data`() = coTest {
        val arrTvList = listOf(ArrudeiaUseCaseEntity(0,""))

        `when`(getAllArrudeiaTvUseCase()).thenReturn(arrTvList)

        viewModel.fetchDataArrTv()

        Assert.assertTrue(viewModel.arrTvUiState.value is ArrudeiaTvUiState.Success)
    }

    @Test
    fun `test fetchDataArrTv with error`() = coTest {
        `when`(getAllArrudeiaTvUseCase()).thenReturn(emptyList())

        viewModel.fetchDataArrTv()

        Assert.assertTrue(viewModel.arrTvUiState.value is ArrudeiaTvUiState.Error)
    }

    @Test
    fun `test fetchDataTravels with valid data`() = coTest {
        val list = listOf( TravelUseCaseEntity(0,""))

        `when`(getAllTravelHomeUseCase()).thenReturn(list)

        viewModel.fetchDataTravels()

        Assert.assertTrue(viewModel.travelUiState.value is TravelUiState.Success)
    }

    @Test
    fun `test fetchDataTravels with error`() = coTest {
        `when`(getAllTravelHomeUseCase()).thenReturn(emptyList())

        viewModel.fetchDataTravels()

        Assert.assertTrue(viewModel.travelUiState.value is TravelUiState.Error)
    }

    @Test
    fun `test getUserPersonalInformation with valid data`() = coTest {
        val user = UserPersonalInformationUseCaseEntity("Test User", "test@test.com", "image")

        `when`(getUserPersonalInformationUseCase()).thenReturn(Result.Success(user))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.userUiState.value is ProfileUiState.Success)
    }

    @Test
    fun `test getUserPersonalInformation with error`() = coTest {
        `when`(getUserPersonalInformationUseCase()).thenReturn(Result.Error(null))

        viewModel.getUserPersonalInformation()

        Assert.assertTrue(viewModel.userUiState.value is ProfileUiState.Error)
    }
}