package com.arrudeia.feature.home.presentation.ui.promotion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.ImagePagerWithDots
import com.arrudeia.feature.home.domain.Affiliate
import com.arrudeia.feature.home.domain.entity.PromotionUseCaseEntity
import com.arrudeia.feature.home.presentation.ui.events.detail.openLinkInChrome
import com.arrudeia.feature.home.presentation.viewmodel.PromotionViewModel


@Composable
fun PromotionScreen() {
    val context = LocalContext.current
    PromotionList(OnClick = { url ->
        url.openLinkInChrome(context)
    })
}

@Composable
private fun PromotionList(
    viewModel: PromotionViewModel = hiltViewModel(),
    OnClick: (String) -> Unit
) {
    if (viewModel.list.isEmpty())
        viewModel.fetchPromotions()
    else if (viewModel.isLoading.value)
        ArrudeiaLoadingWheel()
    else if (viewModel.list.isNotEmpty())
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                items(viewModel.list) {
                    Spacer(modifier = Modifier.size(4.dp))
                    BoxItem(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                OnClick(it.urlClick)
                            })
                }
            }
        }
}


@Composable
private fun BoxItem(item: PromotionUseCaseEntity, modifier: Modifier = Modifier) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                ImagePagerWithDots(
                    item.images,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    maxDots = 3,
                    cardCornerShape = 8.dp
                )
                BoxContent(
                    item, Modifier.padding(
                        top = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun BoxContent(item: PromotionUseCaseEntity, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = item?.name.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            item?.date.orEmpty(),
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 2.dp),
            maxLines = 3,
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            val affiliateIcon = Affiliate.valueOf(item.affiliate).icon
            Image(
                painter = painterResource(affiliateIcon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .height(16.dp)
            )

            Text(
                item.totalValue,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 2.dp)
            )
        }
    }
}

