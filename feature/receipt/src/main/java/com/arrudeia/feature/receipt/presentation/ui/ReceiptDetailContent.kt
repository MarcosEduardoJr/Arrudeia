package com.arrudeia.feature.receipt.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.time
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.feature.receipt.R.string.difficult
import com.arrudeia.feature.receipt.R.string.ingredients
import com.arrudeia.feature.receipt.R.string.method_of_preparation
import com.arrudeia.feature.receipt.R.string.portions
import com.arrudeia.feature.receipt.presentation.model.ReceiptDetailUiModel

@Composable
fun ReceiptDetailContent(
    item: ReceiptDetailUiModel?,
) {
    Column(
        modifier = Modifier
            .padding(top = 240.dp)
            .fillMaxSize()
            .clipToBounds(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.background_grey_F7F7F9),
                contentColor = colorResource(id = R.color.background_grey_F7F7F9),
                disabledContainerColor = colorResource(id = R.color.background_grey_F7F7F9),
                disabledContentColor = colorResource(id = R.color.background_grey_F7F7F9)
            )
        ) {
            Box(
                modifier = with(Modifier) {
                    fillMaxSize()
                })
            {
                contentDescription(item)
            }
        }
    }
}

@Composable
private fun contentDescription(item: ReceiptDetailUiModel?) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clipToBounds(),
    ) {
        items(1) {
            Text(
                text = item?.name.orEmpty(),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            headerContentDescription(item)
            Spacer(modifier = Modifier.size(10.dp))
            include(item)
            Spacer(modifier = Modifier.size(10.dp))
            if (item?.description?.isNotEmpty() == true) {
                Text(
                    text = stringResource(method_of_preparation),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = item?.description.orEmpty(),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
            }

        }
    }
}


@Composable
private fun headerContentDescription(item: ReceiptDetailUiModel?) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            Text(
                text = stringResource(time),
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = " " + item?.getTimeFormatted().orEmpty(),
                color = colorResource(id = colorPrimary),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(portions),
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = item?.quantity.orEmpty(),
                color = colorResource(id = colorPrimary),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(difficult),
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = ReceiptLevelEnum.getStringFromEnum(
                    context,
                    ReceiptLevelEnum.valueOf(item?.level.orEmpty())
                ),
                color = colorResource(id = colorPrimary),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
private fun include(item: ReceiptDetailUiModel?) {
    if (item?.ingredients.orEmpty().isNotEmpty()) {
        Text(
            text = stringResource(ingredients),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxWidth(),
        ) {
            item?.ingredients?.forEach {
                Row {
                    Image(
                        Icons.Rounded.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.colorPrimary))
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = it?.step.orEmpty(),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}