package com.arrudeia.feature.profile.presentation.ui.interest

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.common.R
import com.arrudeia.core.common.R.string.interests
import com.arrudeia.core.designsystem.component.FilterChipGroup
import com.arrudeia.core.profile.InterestsOptions


@Suppress("LongMethod")
@Composable
fun InterestForm(
    interestParamChange: (String) -> Unit,
    biographyParam: String,
    biographyParamChange: (String) -> Unit,
    interestParam: String,
    previousInterests: String,
) {
    val context = LocalContext.current
    LazyColumn(Modifier.padding(horizontal = 16.dp)) {
        items(1) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .wrapContentHeight(Alignment.CenterVertically),
                value = biographyParam,
                onValueChange = biographyParamChange,
                label = { Text(text = stringResource(R.string.biography), color = Color.Black) },
                minLines = 6,
                maxLines = 6,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    cursorColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                shape = RoundedCornerShape(16.dp),
            )

            Spacer(modifier = Modifier.size(16.dp))

            val previousFormatter = if (interestParam.isEmpty()) previousInterests.split(",")
                .map { InterestsOptions.valueOf(it).getString(context) } else emptyList()
            FilterChipGroup(
                title = stringResource(id = interests),
                chipLabels = InterestsOptions.getAllResourceNames(context),
                onSelectionChanged = { selectedChips ->
                    selectedChips.map { InterestsOptions.getEnumFromString(context, it)?.resourceName.orEmpty() }
                        .joinToString(separator = ",") { it }
                        .let(interestParamChange)
                },
                previousSelected = previousFormatter,
            )
            Spacer(modifier = Modifier.size(100.dp))
        }
    }
}