package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp


@Composable
fun DropDown(
    listDrop: List<DropListUiModel>,
    chooseDrop: (DropListUiModel) -> Unit,
    dropChoosed: DropListUiModel,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)
) {
    var expanded by remember { mutableStateOf(false) }
    RectangleItemButton(
        Icons.Rounded.Build,
        dropChoosed.name,
        modifier
            .clickable { expanded = !expanded })
    if (expanded)
        listDrop.forEach {
            DropdownMenuItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .background(Color.White),
                text = { Text(it.name, color = Color.Black) },
                onClick = {
                    chooseDrop(it)
                    expanded = false
                },
            )
        }

}

class DropListUiModel(
    val name: String = "",
    val icon: ImageVector = Icons.Rounded.Build,
    val id: Int = 0
)