package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipGroup(
    title: String,
    chipLabels: List<String>,
    onSelectionChanged: (List<String>) -> Unit,
    previousSelected: List<String>
) {
    val selectedChips = remember { mutableStateListOf<String>() }
    if (previousSelected.isNotEmpty())
        selectedChips.addAll(previousSelected)
    Card(
        shape = RoundedCornerShape(50f),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text(
                text = title,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            ) {
                chipLabels.forEach { label ->
                    val isSelected = selectedChips.contains(label)
                    ArrudeiaTextTopicTag(
                        followed = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedChips.remove(label)
                            } else {
                                selectedChips.add(label)
                            }
                            onSelectionChanged(selectedChips)
                        },
                        text = {
                            Text(text = label, color = if (isSelected) Color.White else Color.Black)
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}