package com.arrudeia.feature.tips.presentation.ui

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arrudeia.core.common.R.string.aid
import com.arrudeia.core.common.R.string.checklist
import com.arrudeia.core.common.R.string.receipts
import com.arrudeia.core.data.navigation.aidRoute
import com.arrudeia.core.data.navigation.checkListRoute
import com.arrudeia.core.data.navigation.receiptRoute
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.HtmlText
import com.arrudeia.feature.tips.R.string.headeMessageTips

@Composable
internal fun TipsRoute(routeClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            TipsHeader(
                modifier = Modifier
                    .fillMaxWidth(),
            )

            LazyColumn(modifier = Modifier) {
                items(1) {

                    CustomTipCard(
                        Modifier
                            .padding(top = 60.dp)
                            .clickable { routeClick(receiptRoute) },
                        com.arrudeia.feature.tips.R.drawable.ic_food,
                        stringResource(id = receipts)
                    )

                    CustomTipCard(
                        Modifier
                            .padding(top = 40.dp)
                            .clickable { routeClick(checkListRoute) },
                        com.arrudeia.feature.tips.R.drawable.ic_checklist,
                        stringResource(id = checklist)
                    )


                    CustomTipCard(
                        Modifier
                            .padding(top = 40.dp)
                            .clickable { routeClick(aidRoute) },
                        com.arrudeia.feature.tips.R.drawable.ic_aid,
                        stringResource(id = aid)
                    )

                    Spacer(modifier = Modifier.size(100.dp))
                }
            }
        }
    }
}


@Composable
fun CustomTipCard(modifier: Modifier, imageRes: Int, text: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .size(150.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(50.dp))
            )
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}


@Composable
fun TipsHeader(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {

        HtmlText(
            modifier = Modifier
                .align(Alignment.CenterStart),
            html = stringResource(id = headeMessageTips),
            normalColor = R.color.colorBlack,
            alignmentText = View.TEXT_ALIGNMENT_TEXT_START,
            textStyle = MaterialTheme.typography.titleMedium
        )


    }
}