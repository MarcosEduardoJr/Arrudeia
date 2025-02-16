package com.arrudeia.core.designsystem.component

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.R.string.adult
import com.arrudeia.core.designsystem.R.string.child
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import com.arrudeia.core.designsystem.R.string.max_people_count
import com.arrudeia.core.designsystem.R.string.age_of_child_of

@Composable
fun ArrudeiaPeopleCount(
    modifier: Modifier,
    adult: Int,
    child: Int,
    onAdultChildrenChange: (
        adult: Int,
        child: Int,
    ) -> Unit,
    maxCount: Int,
    childAgeListValueChange: (listAges: List<Int>) -> Unit,
) {

    var showDialog by remember { mutableStateOf(false) }
    PeopleCountDialog(
        showDialog,
        { showDialog = it },
        onAdultChildrenChange,
        adult,
        child,
        maxCount,
        childAgeListValueChange
    )


    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape)
            .background(Color.White)
            .clickable { showDialog = true }
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Text(
                text = "${adult + child}",
                color = Color.Black
            )
        }
    }
}


@Composable
fun PeopleCountDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    onAdultChildrenChange: (adultParam: Int, childParam: Int) -> Unit,
    adultParam: Int,
    childParam: Int,
    maxCount: Int,
    childAgeListValueChange: (listAges: List<Int>) -> Unit,
) {
    var adultValue by remember { mutableIntStateOf(adultParam) }
    var childValue by remember { mutableIntStateOf(childParam) }
    val childAgeListValue = remember { mutableStateListOf<Int>() }

    var isEnableIncrease by remember { mutableStateOf(true) }

    isEnableIncrease = maxCount != (adultValue + childValue)

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
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.background_grey_F7F7F9))
                            .fillMaxWidth()
                            .defaultMinSize(300.dp),
                        verticalArrangement = Arrangement.Top
                    ) {

                        Row(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(colorResource(id = R.color.background_grey_F7F7F9))
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
                            val saveEnabled = adultValue > 0 || childValue > 0
                            TextButton(
                                onClick = {
                                    onAdultChildrenChange(adultValue, childValue)
                                    childAgeListValueChange(childAgeListValue)
                                    onShowDialogChange(false)
                                },
                                enabled = saveEnabled
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save),
                                    color = if (saveEnabled) colorResource(id = R.color.colorPrimary)
                                    else colorResource(id = R.color.text_grey)
                                )
                            }
                        }


                        Text(
                            if (!isEnableIncrease) stringResource(id = max_people_count) + " $maxCount " else "",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color.Black,
                        )

                        CounterCard(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .align(Alignment.CenterHorizontally),
                            stringResource(id = adult),
                            adultValue,
                            { adultValue = it },
                            isEnableIncrease
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        CounterCard(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            stringResource(id = child),
                            childValue,
                            {
                                if (it > childValue)
                                    childAgeListValue.add(1)
                                else
                                    if (childAgeListValue.isNotEmpty())
                                        childAgeListValue.removeAt(childAgeListValue.size - 1)
                                childValue = it
                            },
                            isEnableIncrease
                        )

                        Spacer(modifier = Modifier.size(12.dp))
                        childAgeListValue.forEachIndexed { i, item ->
                            CounterCard(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                stringResource(id = age_of_child_of) + " ${i + 1}",
                                childAgeListValue[i],
                                {
                                    childAgeListValue[i] = it
                                },
                                childAgeListValue[i] < 17,
                                1
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }
    }
}


