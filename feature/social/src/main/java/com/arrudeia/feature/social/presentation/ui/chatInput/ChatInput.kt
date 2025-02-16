package com.arrudeia.feature.social.presentation.ui.chatInput

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.color.text_grey


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
internal fun ChatInput(
    modifier: Modifier = Modifier,
    onMessageChange: (String) -> Unit,
) {

    val context = LocalContext.current

    var input by remember { mutableStateOf("") }
    val textEmpty: Boolean by derivedStateOf { input.isEmpty() }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        TextField(
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
                .background(Color.White)
                .shadow(8.dp, CircleShape, spotColor = Color.Black, ambientColor = Color.Black),
            value = input,
            onValueChange = {
                input = it
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            placeholder = {
                Text(text = "Message", color = Color.Black)
            },

            )



        Spacer(modifier = Modifier.size(8.dp))

        FloatingActionButton(
            shape = CircleShape,
            modifier = Modifier,
            onClick = {
                if (!textEmpty) {
                    onMessageChange(input)
                    input = ""
                }
            },
            containerColor = if (textEmpty) colorResource(id = text_grey) else colorResource(
                id = colorPrimary
            ),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = if (textEmpty) Icons.Filled.Send else Icons.Filled.Send,
                contentDescription = null, tint = Color.White
            )
        }
    }
}