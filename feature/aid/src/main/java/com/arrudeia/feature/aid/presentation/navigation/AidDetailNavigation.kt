package com.arrudeia.feature.aid.presentation.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.arrudeia.core.data.navigation.receiptDetailtRoute
import java.net.URLDecoder
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
const val RECEIPT_DETAIL_ID_ARG = "receiptDetailIdArg"

internal class AidDetailArgs(var id: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[RECEIPT_DETAIL_ID_ARG]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToAidDetail(id: String) {
    val encodedId = URLEncoder.encode(id, URL_CHARACTER_ENCODING)
    this.navigate("$receiptDetailtRoute/$encodedId") {
        launchSingleTop = true
    }
}

