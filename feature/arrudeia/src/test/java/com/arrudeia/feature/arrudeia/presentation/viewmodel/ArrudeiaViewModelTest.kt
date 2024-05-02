import android.net.Uri
import com.arrudeia.core.result.Result
import com.arrudeia.core.test.ViewModelTest
import com.arrudeia.feature.arrudeia.domain.GetAllArrudeiaPlacesUseCase
import com.arrudeia.feature.arrudeia.domain.SaveArrudeiaPlaceUseCase
import com.arrudeia.feature.arrudeia.domain.entity.ArrudeiaPlaceDetailsUseCaseEntity
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.ui.AvailableOptions
import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions
import com.arrudeia.feature.arrudeia.presentation.ui.SubCategoryOptions
import com.arrudeia.feature.arrudeia.presentation.viewmodel.ArrudeiaViewModel
import com.arrudeia.feature.arrudeia.presentation.viewmodel.SaveMarkerUiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.arrudeia.feature.arrudeia.R
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ArrudeiaViewModelTest : ViewModelTest() {

    @Mock
    private var getAllArrudeiaPlacesUseCase = Mockito.mock(
        GetAllArrudeiaPlacesUseCase::class.java
    )

    @Mock
    private var saveArrudeiaPlaceUseCase = Mockito.mock(
        SaveArrudeiaPlaceUseCase::class.java
    )

    private lateinit var viewModel: ArrudeiaViewModel

    @Before
    fun setUp() {
        viewModel = ArrudeiaViewModel(saveArrudeiaPlaceUseCase, getAllArrudeiaPlacesUseCase)
    }

    @Test
    fun `test getPlacesMarker with valid data`() = coTest {
        val placeList = listOf(
            ArrudeiaPlaceDetailsUseCaseEntity(
                uuid = "",
                name = "",
                categoryName = CategoryOptions.CATEGORY_FOOD.name,
                subCategoryName = SubCategoryOptions.SUBCATEGORY_AIRPORT.name,
                phone = "",
                socialNetwork = "",
                description = "",
                image = "",
                location = LatLng(0.0, 0.0),
                rating = 0.0,
                priceLevel = 0,
                comments = listOf(),
                available = listOf()
            )
        )

        `when`(getAllArrudeiaPlacesUseCase()).thenReturn(Result.Success(placeList))

        viewModel.getPlacesMarker()

        Assert.assertTrue(viewModel.places.isNotEmpty())
    }

    @Test
    fun `test getPlacesMarker with error`() = coTest {
        `when`(getAllArrudeiaPlacesUseCase.invoke()).thenReturn(Result.Error(null))

        viewModel.getPlacesMarker()

        Assert.assertTrue(viewModel.places.isEmpty())
    }

    @Test
    fun `test savePlace with valid data`() = coTest {
        val place = "Place1"

        `when`(
            saveArrudeiaPlaceUseCase(
                "name",
                "phone",
                "socialNetwork",
                "description",
                viewModel.uri.value,
                viewModel.availables,
                categoryName = CategoryOptions.CATEGORY_FOOD.name,
                subCategoryName = SubCategoryOptions.SUBCATEGORY_AIRPORT.name,
                LatLng(0.0, 0.0),
                0,
               0.0
            )
        ).thenReturn(Result.Success(place))
        viewModel.onTakePhoto(Mockito.any<Uri>())
        viewModel.addAvailables( ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI))
        viewModel.savePlace(
            "name",
            "phone",
            "socialNetwork",
            "description",
            categoryName = CategoryOptions.CATEGORY_FOOD.name,
            subCategoryName = SubCategoryOptions.SUBCATEGORY_AIRPORT.name,
            LatLng(0.0, 0.0)
        )

        Assert.assertTrue(viewModel.saveMarkerUiState.value is SaveMarkerUiState.Success)
    }

    @Test
    fun `test savePlace with error`() = coTest {
        `when`(
            saveArrudeiaPlaceUseCase(
                "name",
                "phone",
                "socialNetwork",
                "description",
                viewModel.uri.value,
                viewModel.availables,
                categoryName = CategoryOptions.CATEGORY_FOOD.name,
                subCategoryName = SubCategoryOptions.SUBCATEGORY_AIRPORT.name,
                LatLng(0.0, 0.0),
                0,
                0.0
            )
        ).thenReturn(Result.Error(R.string.not_possible_save))
        viewModel.onTakePhoto(Mockito.any<Uri>())
        viewModel.addAvailables( ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI))
        viewModel.savePlace(
            "name",
            "phone",
            "socialNetwork",
            "description",
            categoryName = CategoryOptions.CATEGORY_FOOD.name,
            subCategoryName = SubCategoryOptions.SUBCATEGORY_AIRPORT.name,
            LatLng(0.0, 0.0)
        )

        Assert.assertTrue(viewModel.saveMarkerUiState.value is SaveMarkerUiState.Error)
    }
}