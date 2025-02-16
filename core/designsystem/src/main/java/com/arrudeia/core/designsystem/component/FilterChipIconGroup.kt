package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipIconGroup(
    title: String,
    chipLabels: List<Int>,
    onSelectionChanged: (List<Int>) -> Unit,
    previousSelected: List<Int>,
    isMultipleChoice: Boolean
) {
    val selectedChips = remember { mutableStateListOf<Int>() }
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
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            ) {
                chipLabels.forEach { icon ->
                    val isSelected = selectedChips.contains(icon)
                    ArrudeiaTopicTag(
                        followed = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedChips.remove(icon)
                            } else {
                                if (!isMultipleChoice)
                                    selectedChips.clear()
                                selectedChips.add(icon)
                            }
                            onSelectionChanged(selectedChips)
                        },
                        content = {
                            Box(modifier = Modifier.padding(4.dp).clip(CircleShape)) {
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                tint =  Color.Unspecified,
                                modifier = Modifier
                            )
                            }
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}