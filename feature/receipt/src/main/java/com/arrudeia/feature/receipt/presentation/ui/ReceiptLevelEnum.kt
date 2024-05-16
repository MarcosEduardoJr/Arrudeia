package com.arrudeia.feature.receipt.presentation.ui

import android.content.Context

enum class ReceiptLevelEnum(val resourceName: String) {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    companion object {
        fun getStringFromEnum(context: Context, enumValue: Enum<*>): String {
            val resId =
                context.resources.getIdentifier(enumValue.name, "string", context.packageName)
            return if (resId != 0) {
                context.getString(resId)
            } else {
                ""
            }
        }
    }

    fun getString(context: Context): String {
        val resId = context.resources.getIdentifier(resourceName, "string", context.packageName)
        return if (resId != 0) {
            context.getString(resId)
        } else {
            ""
        }
    }
}
