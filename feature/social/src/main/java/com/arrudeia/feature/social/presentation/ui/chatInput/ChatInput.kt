package com.arrudeia.feature.social.presentation.ui.chatInput

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.arrudeia.feature.social.presentation.chat.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
internal fun ChatInput(
    modifier: Modifier = Modifier,
    onMessageChange: (String) -> Unit,
    onFocusEvent: (Boolean) -> Unit
) {

    val context = LocalContext.current

    var input by remember { mutableStateOf(TextFieldValue("")) }
    val textEmpty: Boolean by derivedStateOf { input.text.isEmpty() }

//    val imePaddingValues = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.ime)
//    val imeBottomPadding = imePaddingValues.calculateBottomPadding().value.toInt()
    val imePaddingValues = PaddingValues()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.extraSmall),
        verticalAlignment = Alignment.Bottom
    ) {

//        ChatTextField(
//            modifier = modifier.weight(1f).focusable(true),
//            input = input,
//            empty = textEmpty,
//            onValueChange = {
//                input = it
//            }, onFocusEvent = {
//                onFocusEvent(it)
//            }
//        )
//
//        Spacer(modifier = Modifier.width(6.dp))
        TextField(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .weight(1f).padding(start = 16.dp, end = 8.dp)
                .focusable(true)
                .clip(CircleShape)
                .background(Color.White),
//                .padding(bottom = MaterialTheme.spacing.extraSmall),
            value = input,
            onValueChange = { input = it },
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
            }




        )
        FloatingActionButton(
            shape = CircleShape,
            modifier = Modifier.padding(end = 16.dp),
            onClick = {
                if (!textEmpty) {
                    onMessageChange(input.text)
                    input = TextFieldValue("")
                } else {
                    Toast.makeText(
                        context,
                        "Sound Recorder Clicked.\n(Not Available)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            containerColor =  if (textEmpty) colorResource(id = R.color.text_grey)  else colorResource(id = R.color.colorPrimary),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = if (textEmpty) Icons.Filled.Send else Icons.Filled.Send,
                contentDescription = null, tint = Color.White
            )
        }
    }
}