package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R

@Composable
fun CounterCard(
    modifier: Modifier,
    name: String,
    counter: Int,
    counterChange: (Int) -> Unit,
    isEnableIncrease: Boolean,
    minDecrease: Int = 0
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = name,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                if (counter > minDecrease-1)
                    counterChange(
                        if (counter > minDecrease)
                            counter - 1
                        else counter
                    )
            }
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrement", tint = Color.Black)
            }
            Text(
                text = counter.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            IconButton(onClick = { if (isEnableIncrease) counterChange(counter + 1) }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increment",
                    tint = if (isEnableIncrease) Color.Black else colorResource(
                        id = R.color.text_grey
                    )
                )
            }
        }
    }
}