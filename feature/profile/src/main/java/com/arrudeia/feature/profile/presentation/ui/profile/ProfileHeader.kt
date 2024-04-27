package com.arrudeia.feature.profile.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.presentation.model.ProfileUiModel


@Composable
fun profileHeader(user: ProfileUiModel, modifier: Modifier) {
    Spacer(modifier = Modifier.size(30.dp))

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        title(modifier = Modifier.align(Alignment.Center))
    }

    Spacer(modifier = Modifier.size(30.dp))


    Box(
        modifier = modifier
    ) {

        profileShorDetail(
            Modifier
                .align(Alignment.Center), user
        )
    }
}

@Composable
fun title(modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.profile),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}
