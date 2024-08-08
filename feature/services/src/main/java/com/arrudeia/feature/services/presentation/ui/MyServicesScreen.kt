package com.arrudeia.feature.services.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R
import com.arrudeia.feature.services.presentation.model.ServiceDetailUiModel

@Composable
fun MyServicesScreen(
    item: ServiceDetailUiModel?,
) {
    val context = LocalContext.current
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
fun contentDescription(item: ServiceDetailUiModel?) {
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

            Spacer(modifier = Modifier.size(10.dp))

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

