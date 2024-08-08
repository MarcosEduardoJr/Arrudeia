package com.arrudeia.feature.checklist.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R
import com.arrudeia.feature.checklist.R.string.tip_of_bring
import com.arrudeia.feature.checklist.presentation.model.CheckListUIModel

@Composable
fun content(
    item: List<CheckListUIModel?>
) {
    Column(
        modifier = Modifier
            .padding(top = 260.dp)
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
private fun contentDescription(list: List<CheckListUIModel?>) {
    val context = LocalContext.current
    Column {

        val GRID_CELLS_COUNT = 2
        Text(
            text = stringResource(id = tip_of_bring),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .clipToBounds(),
            columns = GridCells.Fixed(GRID_CELLS_COUNT),
            contentPadding = PaddingValues(horizontal = 8.dp),
        ) {
            items(list) { item ->
                var checkListItem : String = ""
                item?.let {
                    it.name.let { name ->
                        checkListItem = name
                    }
                }
                Text(
                    text = ChecklistNameEnum.getStringFromEnum(context, ChecklistNameEnum.valueOf(checkListItem)),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}