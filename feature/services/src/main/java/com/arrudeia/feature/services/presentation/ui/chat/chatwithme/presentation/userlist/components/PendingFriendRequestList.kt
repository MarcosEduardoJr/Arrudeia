package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.model.FriendListRegister
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.ui.theme.spacing

@Composable
fun PendingFriendRequestList(
    item: FriendListRegister,
    onAcceptClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxSize().padding(
               MaterialTheme.spacing.small,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    vertical = MaterialTheme.spacing.medium,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                text = item.requesterEmail,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Row() {
                TextButton(
                    onClick = { onCancelClick() }
                ) {
                    Text(text = "Decline",
                        color = Color.Black)
                }
                TextButton(onClick = { onAcceptClick() }) {
                    Text(text = "Accept",
                        color = Color.Black)
                }
            }

        }
    }
}