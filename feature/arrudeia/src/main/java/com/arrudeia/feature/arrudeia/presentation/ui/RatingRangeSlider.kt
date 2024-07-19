package com.arrudeia.feature.arrudeia.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R.drawable.ic_star

@Composable
fun RatingRangeSlider(
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(1f) }
    onValueChange(sliderPosition.toInt())
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecione a qualidade do lugar",
            color = Color.Black,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(2.dp))

        Slider(
            value = sliderPosition,
            onValueChange = { newValue ->
                sliderPosition = newValue
            },
            valueRange = 1f..3f,
            steps = 1,
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    modifier = Modifier.clickable { sliderPosition = 1f },
                    painter = painterResource(id = ic_star),
                    contentDescription = "Ruim",
                    tint = if (sliderPosition == 1f) Color.Black else Color.Gray
                )
                Text(
                    modifier = Modifier.clickable { sliderPosition = 1f },
                    text = "Ruim",
                    color = if (sliderPosition == 1f) Color.Black else Color.Gray
                )
            }
            Row {
                Icon(
                    modifier = Modifier.clickable { sliderPosition = 2f },
                    painter = painterResource(id = ic_star),
                    contentDescription = "Médio",
                    tint = if (sliderPosition == 2f) Color.Black else Color.Gray
                )
                Text(
                    modifier = Modifier.clickable { sliderPosition = 2f },
                    text = "Médio", color = if (sliderPosition == 2f) Color.Black else Color.Gray
                )

            }
            Row {
                Icon(
                    modifier = Modifier.clickable { sliderPosition = 3f },
                    painter = painterResource(id = ic_star),
                    contentDescription = "Bom",
                    tint = if (sliderPosition == 3f) Color.Black else Color.Gray
                )
                Text(
                    modifier = Modifier.clickable { sliderPosition = 3f },
                    text = "Bom", color = if (sliderPosition == 3f) Color.Black else Color.Gray
                )
            }
        }
    }
}

// Enum para representar as opções de preço
enum class RatingRange(val label: String) {
    BAD("Ruim"),
    MEDIUM("Médio"),
    GOOD("Bom")
}