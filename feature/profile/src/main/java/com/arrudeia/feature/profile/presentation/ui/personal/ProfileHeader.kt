package com.arrudeia.feature.profile.presentation.ui.personal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.common.R.string.profile

@Composable
 fun header(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource( profile),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}