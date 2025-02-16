package com.arrudeia.feature.social.presentation.ui.chatrow

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arrudeia.feature.social.domain.chat_list.model.MessageStatus

@Composable
fun MessageTimeText(
    modifier: Modifier = Modifier,
    messageTime: String,
    messageStatus: MessageStatus
) {
    val messageStat = remember {
        messageStatus
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = messageTime,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
        )

        Icon(
            modifier = Modifier
                .size(18.dp)
                .padding(start = 4.dp),
            imageVector = Icons.Default.Done,
//        when (messageStatus) {
//                MessageStatus.PENDING -> {
//                    Icons.Default.DoneAll
//                }
//                MessageStatus.RECEIVED -> {
//                    Icons.Default.DoneAll
//                }
//                MessageStatus.READ ->{
//                    Icons.Default.DoneAll
//                }
//            },
            tint = if (messageStatus == MessageStatus.READ) Color.Black
            else Color.White,
            contentDescription = "messageStatus"
        )

    }
}