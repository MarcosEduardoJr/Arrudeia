package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.color.text_grey
import com.arrudeia.core.designsystem.R.string.save


@Composable
fun ArrudeiaDateRangeHotel(
    modifier: Modifier,
    startDate: String,
    endDate: String,
    onDateRangeSelected: (startDate: String, endDate: String) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    DateRagePickerDialog(showDialog, { showDialog = it }, onDateRangeSelected)

    if (startDate.isEmpty()) {
        val dateMilis = System.currentTimeMillis()
        val startDateMillis = Calendar.getInstance().apply {
            timeInMillis = dateMilis
            add(Calendar.DAY_OF_YEAR, 1)
        }.timeInMillis

        val endDateMillis = Calendar.getInstance().apply {
            timeInMillis = dateMilis
            add(Calendar.DAY_OF_YEAR, 2)
        }.timeInMillis

        onDateRangeSelected(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(startDateMillis),
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(endDateMillis)
        )
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape)
            .clickable { showDialog = true }
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Text(
                text = if (endDate.isNotEmpty()) "$startDate - $endDate" else startDate,
                color = Color.Black
            )
        }
    }
}


@Composable
fun DateRagePickerDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    onDateRangeSelected: (startDate: String, endDate: String) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onShowDialogChange(false) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    DateRangePicker(onShowDialogChange, onDateRangeSelected)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    onShowDialogChange: (Boolean) -> Unit,
    onDateRangeSelected: (startDate: String, endDate: String) -> Unit
) {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier.zIndex(1f))

    val state = rememberDateRangePickerState()
    Column(
        modifier = Modifier
            .background(colorResource(id = background_grey_F7F7F9))
            .fillMaxWidth()
            .height(600.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(colorResource(id = background_grey_F7F7F9))
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onShowDialogChange(false) }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            TextButton(
                onClick = {
                    snackScope.launch {
                        onDateRangeSelected(
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                state.selectedStartDateMillis!!
                            ),
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                state.selectedEndDateMillis!!
                            )
                        )
                        onShowDialogChange(false)
                    }
                },
                enabled = state.selectedEndDateMillis != null
            ) {
                Text(text = stringResource(id = save), color = colorResource(id = colorPrimary))
            }
        }
        DateRangePicker(
            state = state,
            modifier = Modifier.weight(1f),
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                titleContentColor = colorResource(id = text_grey),
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                navigationContentColor = Color.Gray,
                yearContentColor = Color.Black,
                disabledYearContentColor = Color.Gray,
                currentYearContentColor = Color.Black,
                selectedYearContentColor = Color.Black,
                disabledSelectedYearContentColor = Color.Gray,
                selectedYearContainerColor = Color.Black,
                disabledSelectedYearContainerColor = Color.Gray,
                dayContentColor = Color.Black,
                disabledDayContentColor = Color.Black,
                selectedDayContentColor = Color.White,
                disabledSelectedDayContentColor = Color.Gray,
                selectedDayContainerColor = colorResource(id = colorPrimary),
                disabledSelectedDayContainerColor = Color.Gray,
                todayContentColor = colorResource(id = colorPrimary),
                dayInSelectionRangeContentColor = Color.White,
                dayInSelectionRangeContainerColor = colorResource(id = colorPrimary).copy(alpha = 0.6f),
                dividerColor = colorResource(id = text_grey).copy(alpha = 0.6f),
                headlineContentColor = Color.Black,
            )
        )
    }
}
 