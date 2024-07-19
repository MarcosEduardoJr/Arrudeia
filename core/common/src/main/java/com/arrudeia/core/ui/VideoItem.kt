package com.arrudeia.core.ui

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItem(
    val mediaItem: MediaItem,
    val name: String,
    val contentUrl: String,
)