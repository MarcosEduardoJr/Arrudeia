package com.arrudeia.feature.tours.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.common.R.string.information
import com.arrudeia.core.common.R.string.products
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.TextSwitch
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.tours.presentation.navigation.param.ToursStoreParam
import com.arrudeia.feature.tours.presentation.viewmodel.ToursStoreViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ToursStoreScreen(
    onBackClick: () -> Unit,
    args: ToursStoreParam,
    viewModel: ToursStoreViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    // val state by viewModel.hotelDetailState.collectAsState()
    var message by remember { mutableStateOf("") }

    LaunchedEffect(message) {
        if (message.isNotEmpty())
            onShowSnackbar(message, "")
    }

    /*   viewModel.fetchHotelDetail(
           query,
           checkInDate,
           checkOutDate,
           adults,
           children,
           childrenAges,
           propertyToken
       )

       when (state) {
           is HomeViewModel.HotelDetailState.Loading -> {
               Box(
                   Modifier
                       .fillMaxSize()
               ) {
                   ArrudeiaLoadingWheel(
                       modifier = Modifier
                           .fillMaxWidth()
                           .align(Alignment.Center)
                           .height(50.dp),
                   )
               }
           }

           is HomeViewModel.HotelDetailState.Success -> {

               val result = (state as HomeViewModel.HotelDetailState.Success).data
           */    ToursStoreContent(
        //   result,
        onBackClick,
        //    amenities
    )
    /*   }

       is HomeViewModel.HotelDetailState.Error -> {
           message = (state as HomeViewModel.HotelDetailState.Error).message

       }
   }
*/

}


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun ToursStoreContent(
    //  item: HotelDetailResponse,
    onBackClick: () -> Unit,
    // amenities: List<String>
) {
    val imgUrl =
        "data:image/png;base64,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"
    val imgTravelUrl =
        "data:image/jpeg;base64,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"

    ArrudeiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.background_grey_F7F7F9)
            ) {
                Box(
                    modifier = with(Modifier) {
                        fillMaxSize()
                    })
                {

                    GlideImage(
                        model = imgTravelUrl,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        contentDescription = null,
                    )
                    // HotelDetailContent(item = item, amenities)
                    SwitchTabTursStore(

                    )
                }
            }
            buttonBottom(
                onBackClick, Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SwitchTabTursStore(

) {
    val pages = listOf(
        stringResource(id = products),
        stringResource(id = information),
    )
    var pagerState = rememberPagerState(initialPage = 0) { pages.size }
    var selectedTab by rememberSaveable { mutableIntStateOf(pagerState.currentPage) }


    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    Box(
        modifier = Modifier
            .padding(top = 260.dp)
            .fillMaxSize()
            .clipToBounds()
            .clip(RoundedCornerShape(12.dp))
            .background(color = colorResource(id = R.color.background_grey_F7F7F9)),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.background_grey_F7F7F9)),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "Jk Turismo",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            Box(modifier = Modifier.padding(16.dp)) {
                TextSwitch(
                    modifier = Modifier
                        .height(56.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.White)
                        .padding(8.dp),
                    selectedIndex = selectedTab,
                    items = pages,
                    onSelectionChange = {
                        selectedTab = it
                    }
                )
            }

            HorizontalPager(state = pagerState) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    when (currentPage) {
                        0 -> {
                            ToursStoreProducts()
                        }

                        1 -> {
                            ToursStoreSupport()
                        }
                    }
                }
            }
        }

    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ToursStoreProducts() {
    val imgTravelUrl =
        "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTEhMVFhUXGBUVGBgYFxsYHRoVFxcXGBgXFxgbHSggHRolHRcXITEhJSorLi4uGB8zODMsNygtLisBCgoKDg0OGxAQGzUlHyUtLS0tLy01Ly0tLS0tLS0tLS0tLTUtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAMIBAwMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAADBAACBQEGB//EAEcQAAEDAgMEBwYCCAQEBwEAAAEAAhEDIQQSMQVBUWETInGBkbHwBjJCocHRUlMUFRZiktLh8SNygpMzQ4OiBzSjssPi4yT/xAAaAQADAQEBAQAAAAAAAAAAAAABAgMEAAUG/8QANBEAAgIBAwICCAYCAgMAAAAAAAECEQMEITESQRNRBSIyYZGhsfAUQlJxgdHB4UOiFSMz/9oADAMBAAIRAxEAPwDdFFWFFblHZwe2Qg1dnvbq0xxX0X4iLdHmeE1uK7Pw4zgnQee5eupVNF5vC4ZznQ3Veh2fg3BvXJngserae7ZowKgmIZmaWkGHAg9hXl/1E7M4EwLx9JXrqptHglRs45s+abRdQw5njTp0PPGpcnlcVsZzdNFmuor6DicJmbAAlePrYcybFb9PqXNbmbNhUeDM6NdFNNupKMpLV1kOkV6Nc6FPOphUyIKZ3SIvohD6JaJphDNFMpiuIiaKr0adNJRlG6brB0htkYdl3P7k2KQHupJ9HgIHarYSoRaVnmnK5WWi62oNTOVxkr1WyBDBfW6R2PgWuOZwzQbA6T9Vsvwx1bA5Lz9RlT9U1YoNbjTHBcqNBCTLHojc0XWPpL2K4qnuheeq4A5pJ3rZq7ZpyQdyxtobVB90ELdgjkW1GfJKHmaDcQ1gGgRKe0WHevLuqud1bmdEX9V1subKY9blZ6eP5mS8Z/lRs7X2swsIY6680XIjMM47jbVFoUBmGbTer44RxqkSnKU3bAtY47lenhy4wBJXusJQYGiGjTgsl1ENquLTrqs61fVdIq9PVbmH+q3bx2RxT+A9nS+75aPmVuYQzwMJj9KAmVKeqycIpHBDlnka+xKgcQGkjcV1ekO0eSiZanL5C+BjE8DWyWOi1qeIBCVp4OHCYIPkhYtrWO6u/dwUJ9M5bF43FbmgxzGaABBxOPG4oNGXiB5lVZs0kTvSKMU/WYzbrYpTxbi4Lbpg2Xn2Ycn3QZ5IlOtUbbMe9PkxqXsiRnXJtueAla9MOkHQpB7KpGYm3z8EPp3cUkcT7MZzXcXxGADbJA0oWnVJOpQDSW2EmluzPJLsImmnMLszO0umNfHmmMJRGa4lafRhunV5JMudrZDQxp7s87+jQ4A6WuOHJH2pDsuUaCP7pjGsBd1UuyQnU7qQrVWjP6BQUY3rSZhySOeiriMOW6hP4u9CdHcz3UlUUE3lUyJusHSF2fi3MNphemwWLBC8xTYL5jHDtT9CuBYaLHngpF8UmjYx+PDGyLrBxu0ajxEwOVvmmcQQRBKVFHglxRhFb8jTk2ZFSiUFtEkwt7I02m/y7VTF0GNcMtxC1rP2IPF3LHDsZ0Tw0gMF9JJ5pjG7UZli4J5Sq/rFgbEfJZ+LxOe0DlAWdRcn6yKuSitircW0Aho11KthujacxHNK4fDZnATF9U7idmhtmvknjCtLpW18kouT3obftlosLpJwfUMgQOSE/Z+VzQTmnXKt3CwGwBu4KUnDGriUXVPaRzBYGBBeexZeODmuIbczAG8rSxOOyiGm/NZ+GbLiXtzExrEBJBveUgzr2URmzcQ4T1RO4m66tY4hw3jxUS+NPyQfDj5sZp1wReAlcUwTBieKXDuBVsxPxDvagoU7H6rLshu6U43EQLAJA9oVHHgQuceo5So1GVgDbU+a7Ua03OqzQJ4K5qPFrRySdHkN1DlalpDrb/XBAxmDuCzTegGpzI7pV+ldxKZKS7gdME1m4hEDY09FVdU3W70HNxT7sXZF7gyDCOcSTqEvWNraqlOqR5yuavcF0FNHP7rYhUNIuERou9MZkQq1K8mwA4wirO2LNMAHQiYspQLnOtrzj6rrHcYI7VRrouEDgdag6ScumttFx2GIiYv6unKmKkAGD9lQYgRBaEVOXkDpQKtsoSJvxvHgiM2ZTvAdbfNlapXkXtCrTxMDRK3Nrkao2Ep0GtF2gz3rrwMwgTbSLT3ILsQN1l12JkydyFM7YB+iOylx0HrRL9FoJ1Tjq+u8c0JzwdwCopSEcUXxGCYxl5Lidf6cEvTpEe7abSmqleWBveEMYkgQgpSoLUbCUMCMolvek3UGgwJMbwjnEErtOoB/ZcpSRzSYXCHKCL33b/HVWe9swwEdp+6WY8yb+IXXVZ4JGtw3sVr0bXddVy2sV1zwhuqDj2I2xdiFn7x8F1LPx7WmC9oPAkT81F1nGH+2TvyB/uH+RWb7ZO/Kb/uH+VeU6M8ArCi7gF6zw4/I+c/H6j9XyR6we2L/AMtn+4f5VZvtc78FPvqH+VeYoYV7jDWyeABPyCcGyqoEuY9o4mm4DxU5Y8S7Dx1uqff6G9+1h/DS/wBw/wAqn7WcqX8Z/lWD0Jb8QParhh/FdJ4ePyKLWZ/P6G5+1J/DT/iP8q472rI+Bv8AEfssduEn/mAdro+iYw/s66pJa9lv3wOc33JWsS5GWfVz9j/A272xP5Tf4j9lX9sXfkt/iP2Ub7HvtNaiP+oPpKFW2DSp2qYpk8GBzj5WQeTTr7YUvST7fQN+2LvyW/xH7KD2td+S3+I/ZImhhhA//qfzaxjR4udPyRmUMN+XiT21KY+QBUnqNMvtlo6f0lLuvl/Qx+1jvyW/xH7Lv7Tv/JHifsr4bGU2e5hnkcHPJvx9xMO9oY0w1MHmST9ApvVYu0TRHR6z82Wv4QmPaZ/5I8T9lb9oqv5A8T9ky72oq2hob2U2/ZBdtWo85pqA8Wsa35iEq1mJ9vr/AEU/A6pf8nyX9lD7QVfyB/Efsrfr6t+SB/qP2RDWzH/FqVRMau7LkZt2qUp1aQM53zcWJJiTGhTLPB7KIJaXPHd5aX7I0KG1ajtWAdkn7JsV6h+HxlZtF7NxdykG54JxhYB1nQklmV8FoYJ1vKxoPq/hkcs32Q6tasNKc+P2SNLEtdEBwlod1hGu7k4bwhYl43k+AK5TdnSg0uR12Kr/AJJ+f2XP0qtEmg7x/oszN++L/uq7aAiS+AdDGtyPon6iO/6vp/QzW2hUH/Ljtv8AUJN21qoN4jgG7u3OiOwg4/IIL8G0+t/gimu4knPtL6f0UqbZqcY/6c2/3PmuHbFT8wd1IH/5Vc4VupYXm3xkHdbT1ZbWBwdLo4NFkz+IkcRM+HepTywXZlYYc8uJr5f0Yn63f+Z/6H/7KVPaFrGF1SqRF5FDd2dLcytnE0WtImjSDSWyS3ObmMpEiDpfektrbJwuKaKQdTZ8WQTTOgglnvEToTwm++TzwfHJZafOt5SVfsfPfaP2vqVKsUa7gxoOUgFkkghxIknSQPFZZ9o6oAfUrvLmEdED1spggvJJ+Zk9gXqMR/4eQXdHXYYka5spMXcQGuve4aQN/FYVT2HrOq9G4AEEjNLcpgCw6wOaNxA0JWSXW3vZqUUkeeOILus45idXEyTzkmVF7en7H0nAFxw7SdxxD2kdoyGDyUSUNRq9EPxeQ+iYw9OmDeXeuxejrbJwTIB6Rzt+RzLcbwFapsjBOaC19RsXkuaQRpBLjxX0D1MfefPR0GVPZR+YhgqmHa7N0bcw39a3g5bf65ZljNTg7i1zhbiC5YmKwOHbGRwJm+bM4RwGQa/6kIbMvIeI4CmfN5WeU8Uu5shh1EFSSN6pt9jR1Wsni1kJfEbcblEDUCYYNfFZdV7GWi/YzyAPmhUnA2AceQ9BQ8XCnRqWHUNcr7/kffjqB16V37oY2PEulI1cXSaS7ojGmgnXS29aNHDACC7KPXAqmJMC5n/WfuqRyR8hZYcn6l8P9mBjMbSN2Ncx24tJHiCSEocS/dVqDtIjyW2arB28Z8kpVIO/vu76J6xvmPzYjhqO2T/qjPbjqw91889Z8QiYfHvzAvJgXgHU85GnYmm4RhuXMP8ApIPki/qpp+Jkf5svyKZLAvyf5+pCcNY+Mq+FfQyDiqsyXevBd/Sn/i+Q+y3G7AIkwYjUGRHbKH+onH3Wnju070/j4FwvkjLLR66S/wDp82Ypru3F3iVHVqgkHNM6yZETpfmtZmx3tuWmOenyufBN7M2R/hsFVzXVQwS5hJByt38TAF+SMtTjvgktFqkm3Lf93/P8nm2551c7vP0lP0qERmDWzxLp1jhyK3sLgWdIATAObuEG/ch7RxLelpsFw5ryHAyCQ4Ej/uJ8VCWpjkdR8r+Jsw4MuGDlkVtOvgAwtMfu+KcB10A7VWnTH4R4BXfh83AdjQoNb8mpaiLVOJyoWNbYsHAW8lgV8eRWbT6J0OBOcNc5oI3HLpIMrY/R3wQHXsZvymBNtEnVwDjUzZo5XPrvXJs6c4dvv5Fnh2km3AnTlP1R8LVyuaSCQJAGuvGfMcF1tJ0AF4tf3B5iCmG0hGt/W5UclXBmTkntKxR9VwBlziSSYgWHARu5Ru1XDJvefXFFq0DczMnkI7ICCXuG9GLVE8id8jeCcIdJggCOMnTyjvTVDFRxHh915/EYrEggU6dIgm5Lne72ZbeJWjXc4thjg13EjMPCySW5fFkqkb1PGaTuNleu+m8ZalNrmkaOE8o+ZXl9hUqrOkNarnLn5hDcuUQBAEm1h81rdOCYv22jzlZ5QT3N0M7W1hHUGkk0i2mIbFibjjDwTa1zwhDJxUGHMM6zUeJE2MkOi096C+rwPrvVm1+Ki4SLxz4+6Qu7ZzJ/8s075FUQZvPvDyUXMQyXE3+X8qiT1h//AE+74f6FnYUDQeAVm0QNGnyPgrmoPRSrqpmQb9pXrtsyJLyGnkNIAnxRm1mxEEzzhIB44+Sv0s6mVN01TKKxxlBh0JHzR6NIts1wjnbyKVolqs/KefyWbwYJ2tiniSapha7bxM+HmlarRMEk/wDcoQ4fCUKpG+SqIUG+k0AkjXx7rJB73DSfD+qZcJ3aeoQr7gTyjyVExGAbVI3HVM0cSDqFS+gb3wr03tFjMrnNgUUMjEZRaAOWnNXw+0HARIAExAER2BJFw+J3cRqquDblsnkN/daEOtPlAcDUbtFxjM1rhxgzHimdmY5jHZwXNhrjDzAd1YyDW+h7tVgUSb5hEW10PinMOQDx4XO/WbQlnJeQI497NAY5rng5XAgm+YyJ4O1Qalem9zcwgtdLDlkgwQSDO8EjRAqNaLhp7EAvGbQ24fdRlUvkUjBR4956Cm6mfiA5QQiOpt/G3xWJRrHUaeuSaDnASWkjj4JnKiX4eLHTQ4OB7whPw5WbXcOMeKUq1I+KN0A+pXKZOWlXY2X0XcFSSFjtxRYfed4n58kwdpv+LKZ42+YT9RJ6ZrgbfUMb0pUeTuQa+0SfdIHG+YeSXr7RI+Jo7kymiUtNNjPSkIzMRzWK3azybOB7h9lcbRdvyd4jyTOSE/D5FujcbWVK9UmIcWnWQAe4yFmUdosOpaDwBJ+iI7Fs/GEjRyc0PUahA67g48Q2D39YjwARqdYEgBwk8wsh72OB38x91n/q8NIcyo9pBnWb66nrd0pOkrDLD8x7UYGobjKefSU/5lF5UbQxQsMQP9tv1UQpFvExfqNB7uSoam5DdRLolVcGMjM6D2yrKd7Gt0twr3jiuMrjcDfebfKEk/G0/wATnRwm57kN+PAH/DfJ0kfKwTdEvIjLU4lzJGs2sd5XHYgbyAJuSY+SzKBq1CBIpgmwOvhC1WbFY33gHHibpvC/UzFl9KY4+wrFamPY0w0kng0Sl6u0aloYY1vBI8FoVDSZo1s9iz62Jc4mLKsMUTz8npXK36uxSntR46zmO7YTNHaFN1zHMZkmyod5S9XBMOmvELp4IvjYfF6XnHaas2W46ncjid5t9FSljACRmgevlosGKjBAggeKvRxWYwbX3jyss08Lietg1uPLVPc3jVPwnX9yZ7zbwhXeDfNGtjcJGnjIGVpOYxfdbhvB71DUeYJcZ4SdeJtH1WRtm5UPdFLSRlMXN/HqobMTEwCYi4HHtS73Om7ufb32RqxbE5nT2XPOJ0S9fmHpI6vB9zxI/srsqz73VGs+Sz8VWBFmHePsZH90vh3OiLxyM+V012he5v0sUJ6pzH90Txt28tVH7QBs4lvIgtTWx9vhjQxzLNAGYX8RP1XoKeLp1WgtLXA39ArtiE8048o8XU2jlEEEi15B70tU2qDpovWYnAUZno2T/lH2WPjNmUjrTZ3CPJUjGLM0ta12ML9MqOdlbob6Tbjusj1MJUI/4luEQCeYTjaDGe6xo1+aTxVV89XLY6G47DG5WUUlwY8mrnKWzpAG4N7jlDpNrAG08b70PGYI0TlqHIR+IAa750Wbi8TiQ5jmU2sgku6IwXTO90kASYAO9ejoe0FB4dmwtVj4+F5p5iYBOdrzzN2qLyTXELNCgm+rxtvh9/Az6f6NWzZHVc1OxDgDJmLOGg4SlquFA4x2lUGGyuzUiWM93ozD2lgJIDwRDonWEcknX5QPkFqxez6xh1maLydWN151av38/Sv2BfobefYnMPsqm8Zm4ljXiCG5nNINviywDpvSeIoZhEuH+Uwe4pHCYI0o6VhqMnVriHhpF2tuBeFPO2qS/wAFNF05E3Ke/a7+tnpaW0azTDjSqxI67BmjlUZE9pBVKmJ60tblHCS6OwkCQlq9Snboy4zqHMykcpkyh50uPDBPqiq+NfAnn1WX2JtOu+z+Y4Heo/qoki9RU6SHjkGMeTllo10BN92+3atPZuxg85quZ3YCB3SiYLDMZoAj1NpHQGOxaVFLgTPrpZH7jVpUaNNvuAGBrf5pDH7apU9SwR8u1KVcZAuZkgDiSeC8JtPaDG1XuzAnOSCOo0kHUgFx1HI66qWSShyX0OleqbfCXlR9GO0iQHCIIkHiOUpCvjnHUlZuHqvFOixxB6hqCHE/8QzeRbSQOaNnnh64p8ck42ZNZp/AyvHd1X0OueTcqzWxdcyKFip1GY50omInuXSqwoGlCxaOhqHUwzXa+O8IwqcFYVBvCVyCm07Rh4rD5HW03E38bpyjtd2jg06XNoWhAdYgHtRG7EYdWj5/dZ8sIy5PW0vpDJjVcma7EtvLp17B2JR+KAiCP6QvV4XZFMaMb2x9U0zZdPW5HAxHgodEUbP/ACM5cI8J+mOB6pNuc/SZ5pnC4iq5wAaXHkD9JXtW4alFmNO7QK2ZtITTpDmGgBDpj5HfjsnJhnY+LIjqieYt8/mntkbIqUiHPflidDPnuVK20+ks4PaQbX+v0S2M22A0jPlM5Wl2h0nS2/tFuIRjB8cCPUSye819sbSDWk3McJme5eZx236uQkUzLiMoEuIBy5TUA0mSYtYEoVHaRfLD1rTLYvzEEyJ380HauAbVIcXOa9oADpggAAD5BPqdJkxpb891vYcOXEnJZo2b+INR9JlSIDmgyAJBOsjisxlOczhuubgTpeNSl8EMRTF34d8mHe8TH7zGuDJM6xPHcmnVHH3g0kGzg0NOWwDd3VG4czxUsEppVL4ianFgVuG3uKOcgvRSEN0aLSmea0ykroKkKhcmsSi5K4ah7lRxVSOKDdjR2CZlMyGF1GwUEzKIZKi6zjZ6UxCDTETdQuVHOC0WZqKuc9rmua4ksIc2dJBkTOver4f2gq0rUMLhaNQ+9V6Br6jiZmXOIG9UJlVqAFQy44yN+n9IZsUXFPl3uLUaDpL3EZjAtMACwDRwARwDvNuxWYzmrkc00aSpGfJllkk5SdtjeC2/gsJD8VSdVcc2UZmhgDWzLgTmJ3CxF0hi/aqjXe1zaLaALstOmGl2ZuYF1Rzj1Q3Lm05G0XX2lWolrQ9tXUzlDSHRkc25giHNBHfKWbgXViHVBlbubq4jXru1J5aWFliytzlSPpdJ4Gl06nNptrhbt735bP7VhtnY1ziQSHCTBDY7AJJMfffZPyqUMOGwI8EU04JN1ox3FU3Z8/qsqzZOqMVFdkuPv3vcrCsKU81drSV2m6NEzkQUDjABZ1t8rawj2wIj1wWWx06t4K7nEnhdSk7L440beaBuVP0gg747FnU8QY1XGV37yPBTL2aDqx7Fxrr6pI1fH6ozXW+v3QGD4jQxrpuXifaDZThL2SdZbePkvXPq21QgwPkm4MepRToaMpRkpR5PFexobTJphtSq92jJazKYO8kSOYHdpPtdq+z9WlTY+tlyvuQ05sp3BzgPLgkMbsCm8gwWkGQ5puI3zxsm/wBGIHWe954veXm/EmfQQ6jVmzRyx3j63mIvpCBHqyqR61TlSkeCWqs3Ef35I2Y6Fnx2bu9Uc3kjObwQnH1vRUhHFA3IbwikWtdVJTJknEA4qudFqtQnMRsPSQOXS5BDTMyryus5xLyohKLrO6TaIsqRyR3Pj15quW17fZaLMlAlXKu02meSsxsnf4IOR3Sdp6wimn2IYYVcVgyxueA4f2U5Torjwym6SK9DviU7gMJneGwTYkgawLx2mw70hiNpNDsrQRv0C19nB4pvNJ7BWeGwXPazo2EayfidIi3DebZsmT1bR7Ho70fLJqI45rbl/sv7LbQwzGNb1YcSZvmgj3mzyNpSIw07p1KZdVzODAZ6NobrPWPWdExYWGg0TdOi0akcL2SY5tQ3KelMON6uaxpJLbZUtudv3EaNExcd31XXUx2LSpUGunI4GOrrMb7q42W7h6jVN4hiWBoyadExcz3a67l2pTgT67lpDAOHrdvv4JOvs+RF/wCy7xLO8JoRqG+vMdqJRtbdzvdXGF47u7S6j6UHVHqOUQmXnZDdiQw5dZFgFUVLxceuSFTdO+TPzlLYyQxUqcwFRlfL5rkbt/8AXRCqHxXWGhiniCNDru7VDiAOUpMu5+uPYoxxjmFwB11bn69b1QVQQQb89+qXIQ3a/wBUQBa9Eai4PCUi5vrej4chnVBOXWCfULlZg3aQuBSFSLIZRHW+SG4I2K4lSUNxV3dio51rI2d0HHIThKsquPr+qNg6SngouFdRs7pPQhCmXRf7IrTfir02Tw18lVyMihZRtLhvTFLDwASq6etypUxLt1/IdpU5TSW5fFglklUVYPGMAFj380tSbvHWdG/+8WTwoF0kkQIidJ9QqDDA74m8A/VYsuZS4Pf0WjeFPq5YoMMxxDyLjnu3iJVXfpALujruptc4vcGtYbk3OYgkaCE8yg0TFuN9OcEcAmaQZDcknS5MW3R681GU3VHpQuDuLp+42vZfYNCrh2mpmzNqVM7gbvc6D1yRyd4pr2t2MxlAOpU8pa8Em5ljgRJJ/ej5p3/w7oU5quLQXsLetectQEG3awblq+0mDdVw7qbLnK+kYImPgcOYIlBt7OwxhDfbfzPkmCxTm1crSf3uyIFp+a9xsrHl0jLYGNQPqvG0sOGgtkzab7tT38wuZnMlzS5u+2/tG+3FVWRcHn5dK5ScrPotGsHFzCIIiJgyEDE4MaQez5rK2JiCWMc8ySANPiJgxrzXoq7oHHTTf2JzHXmeedSBJ7TqEtVozz+Vl6OpRbvHo/VI4rDQbadn3XdVE3jPPupQb9iEaA1t60stepSi8HmQRERZAqUhAGmomeG+yfqJuJnhxuDcRblbx/sgYhtib+cFMOY5ouLixtHffeuVhIkaG86b4+i5M5oRAJ0M+uG9RpnWx3dyYdTjTw8rJaqeNovpFreu5GwUdgz4rtQEHK4QZuOHGY7U/wCzH6LjC+l0kVgT1XWa+nBksI+IG8nwhPbZ9nHMD61OoKrZEAAyJke9oSDaxm6amtyvgNwv7o88REELj6wIiL+ty7mnTkO5LFsF1z9r6rrIdJeoQbzv08Eu5MyJjzQa4jd/ddZ1AjqhPHj61V3evuqPP1K46ijiqOO9RzlQncmsFHZUQieSiIOlHqadMk3ud3oKlbFtBj4uHZqlae0nlrmsJY2YLmjrEf5te4QhUqQbuOm/fPHhF9VKeenSNWD0baUpsYe8vgjS88SPQXeOumnr6olNs2gAbiTu/tC5iMQ1sCo4TOmp/h5/RZJTcmexhwxxx6Yl8M7JaDadb8j2m6NZx6vu7zwPJJVK4c7MDl5TO7QhDoYokEExF7W8EtFLHqtOJGURAnstw7rIzqZgQdOzW3z3oVCs54MRzM3ud1ly47NBOspGx0eo9gcTlr1QI/xKZsdOq5toPJ7l6baHtfhaMtNVr3NMZaQ6Qi0wcghv+ohfMsXRY+zm5hOm69rz2qjKoHUBgaBogCOfAfZMsjrYOxTaGLbnOVpALnZZa0w03A3iQN2iQxuLdlbNy4Aa21N+1OV6UEZSw+8JMauOuuqy6+BAvmtqeAGhIntRjVkpt0NbO2s5ga0e6HZjJvrAgfZfR8I9uQuBNxNxxC8DsX2eL3A5+oetGp4X5L3NJoY1ouLXmOcR81oR5eRpvYu6oTa1refDcq9LugeHZe90uyq3NDiZ3SfNVrVLxugTAE3trwRIjTnWmBx0+aWe0O7h29yLXdDe4Aa3GkLPNUtIANjwPzC6jmy76QM8rGbkHd9UGthrSMu42G4yu0nwSdDoRMyeOl1xuKubGR2DW9rLqoDoTNGNLaHsnilKrZETppzC1TUBtIiBEcrXnek6uHIcQLbgeMam6NiNHjNqbPc1wq0iWPaZGWxBHxCNF7b2L9uKVdow2KDadWZa6wY95+IDRtQ7xo7dEwk69CRMEHiY0jmvMbY2OHyWzm32gHl2qsZlceTp2PrGN2HQq5suVtUECGHeRIOX4ZkXPcvNbW9nKzG5w0vEdbLdzQ0x1mgngdJAgzC8tsD23r4dvQYgvdTsA8R0tODbKXe8BwN4NjuX07YW0KeJptBd07GtZlqs6n+J1wS4NIe14a5piONgIKdxvdGhwhk/c+bFwgndb7AK47CJOv05LY29T6KqadctquADhUZAdBFi+NTydJ0uNVjOZOh10ncp0YZLpdAHcdUIm3amOjmx1HHjzS1Rsd/rVA7kpU1hDeV19h9ULN2IoPSQu9XXUP1vXUQdJvNtTMW658lWkbt56qKLF2PdQ3TcenAkxBtu+Hcl6urjv4+Ciim+Sq4E9GGE4GDK6w1A03ZnKKKj4J9xxo/xI3D/AOyttEwXAcvqoos79osuBYuNrn4vIJfZ98s79fkuKKq4YrB7VF2f5neYSdE5q1IOuDVgg3EQNyiibGSy+yz6NiXFtNmUxrpbQWVC4hjCDBgf+5RRXXB5L5EGuPSOvud9VM5zNufdHmooulyBcFadV01OsbaXPEp7EmGiLeiooiDsExzRDLbh5LNonrD/ADu8iooiuAS5KYT3Cd+d90ZpmZ/EFFFwBCv7yz8Q45zfj5LqiZCmB7QsGVpgTdT2ExD2YxmR7m5g8OykiQGuImNbqKKi4L4zRw9ZzrucXG2pJ801hR1v9M/9wUUQMrO/A475+qTr6+CiiUMRJ+hStQ3CiiKKoOWjgooogE//2Q=="

    val context = LocalContext.current
    var listResult = listOf("Porto de galinhas", "Maracaipe")
    LazyColumn() {
        items(listResult) {

            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(6.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    GlideImage(
                        model = imgTravelUrl,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        contentScale = ContentScale.Fit,
                    )

                    Column(
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(0.8f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 6.dp, end = 2.dp)
                        )
                    }

                    Text(
                        text = "R$ 100,00",
                        fontSize = 16.sp,
                        color = Color.Black,
                    )
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Black,
                    )

                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}


@Composable
fun ToursStoreSupport() {
}


@Composable
private fun buttonBottom(onBackClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {

        CircularIconButton(
            onClick = {
                onBackClick()
            },
            icon = Icons.Rounded.ArrowBack,
            backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
            iconSize = 50.dp,
            modifier = Modifier
        )
    }
}

