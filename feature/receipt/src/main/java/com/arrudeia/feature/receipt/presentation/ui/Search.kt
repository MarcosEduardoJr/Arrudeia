package com.arrudeia.feature.receipt.presentation.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arrudeia.feature.receipt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(modifier: Modifier, searchTravel: String, onSearchTravelChange: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = searchTravel,
        onValueChange = onSearchTravelChange,
        label = { Text(text = stringResource(R.string.receipt)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(com.arrudeia.core.designsystem.R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Black
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
        shape = RoundedCornerShape(25.dp),
    )
}
