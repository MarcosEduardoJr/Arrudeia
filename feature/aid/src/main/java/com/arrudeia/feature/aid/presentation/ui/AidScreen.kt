package com.arrudeia.feature.aid.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.feature.aid.R
import com.arrudeia.feature.aid.presentation.model.AidUIModel
import com.arrudeia.feature.aid.presentation.navigation.param.AidDetailParam
import com.arrudeia.feature.aid.presentation.viewmodel.AidViewModel
import java.util.Locale


@Composable
internal fun AidRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onReceiptDetailClick: (AidDetailParam) -> Unit,
    onBackClick: () -> Unit,
) {
    val viewModel: AidViewModel = hiltViewModel()
    ReceiptView(viewModel, onShowSnackbar = onShowSnackbar, onReceiptDetailClick,onBackClick)
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
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {

                Row {

                    CircularIconButton(
                        onClick = {
                            onBackClick()
                        },
                        icon = Icons.Rounded.ArrowBack,
                        backgroundColor = colorResource(id = background_grey_F7F7F9),
                        iconSize = 50.dp,
                        modifier = Modifier
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(R.string.first_aid),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )

                }
                Spacer(modifier = Modifier.size(30.dp))

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