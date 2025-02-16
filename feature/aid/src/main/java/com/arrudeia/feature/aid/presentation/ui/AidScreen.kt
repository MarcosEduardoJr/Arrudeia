package com.arrudeia.feature.aid.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.feature.aid.presentation.model.AidUIModel
import com.arrudeia.feature.aid.presentation.navigation.param.AidDetailParam
import com.arrudeia.feature.aid.presentation.viewmodel.AidViewModel
import java.util.Locale


@Composable
  fun AidRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onAidDetailClick: (AidDetailParam) -> Unit,
    onBackClick: () -> Unit,
) {
    val viewModel: AidViewModel = hiltViewModel()
    ReceiptView(viewModel, onShowSnackbar = onShowSnackbar, onAidDetailClick,onBackClick)
}

@Composable
fun ReceiptView(
    viewModel: AidViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onReceiptDetailClick: (AidDetailParam) -> Unit,
    onBackClick: () -> Unit,
) {
    var searchTravel by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                .align(Alignment.TopCenter)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {



                search(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .clip(CircleShape),
                    searchTravel,
                    onSearchTravelChange = { searchTravel = it },
                )
                aid(
                    viewModel, searchTravel, Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(
                            CenterHorizontally
                        ),
                    onReceiptDetailClick
                )
            }
        }
    }
}

fun filterSearchList(search: String, list: List<AidUIModel?>?): List<AidUIModel?>? {
    return list?.filter {
        it?.name?.lowercase(Locale.getDefault())
            ?.contains(search.lowercase(Locale.getDefault())) == true
    }
}