package com.arrudeia.feature.social.presentation.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.arrudeia.feature.social.domain.chat_list.model.FriendListRow
import com.arrudeia.feature.social.domain.chat_list.model.MessageStatus
import com.arrudeia.feature.social.presentation.chat.theme.spacing
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AcceptPendingRequestList(
    item: FriendListRow,
    onChatClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onChatClick() }

    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.small
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(percent = 50),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(64.dp)
                        .background(color = colorResource(id = R.color.colorPrimary))
                        .fillMaxSize()
                ) {
                    if (item.userPictureUrl != "") {


                        GlideImage(
                            model = item.userPictureUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(62.dp)
                                .clip(CircleShape),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }
                if (item.lastMessage.status == MessageStatus.RECEIVED.toString() && item.lastMessage.profileUUID == item.userUUID) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = MaterialTheme.spacing.small),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = item.userEmail,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                            Text(
                                text = "Last Message: " + item.lastMessage.message,
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Black
                            )
                        }
                        Column {
                            Text(
                                text = sdf.format(
                                    item.lastMessage.date
                                ),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                color = Color.Black
                            )

                            Icon(
                                imageVector = Icons.Filled.Build,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                    }
                } else {
                    val dateTimeControl: Long = 0
                    if (!item.lastMessage.date.equals(dateTimeControl)) {

                        if (item.lastMessage.profileUUID != item.userUUID) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = item.userEmail,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            modifier = Modifier.align(Alignment.TopStart)
                                        )
                                        Text(
                                            text = sdf.format(
                                                item.lastMessage.date
                                            ),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color.Black,
                                            modifier = Modifier.align(Alignment.TopEnd)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                    Text(
                                        text = "Me: " + item.lastMessage.message,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Color.Black

                                    )
                                }

                            }

                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = item.userEmail,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            modifier = Modifier.align(Alignment.TopStart)
                                        )
                                        Text(
                                            text = sdf.format(
                                                item.lastMessage.date
                                            ),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color.Black,
                                            modifier = Modifier.align(Alignment.TopEnd)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                    Text(
                                        text = "Last Message: " + item.lastMessage.message,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Color.Black
                                    )
                                }

                            }
                        }

                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = MaterialTheme.spacing.small)
                        ) {
                            Text(
                                text = item.userEmail,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(2.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}