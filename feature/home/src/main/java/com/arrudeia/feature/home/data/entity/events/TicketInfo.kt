package com.arrudeia.feature.home.data.entity.events

import android.content.Context

data class TicketInfo(
    val link: String?,
    val link_type: String?,
    val source: String? //tickets or more info
)

enum class LinkType(val resourceName: String) {
    TICKETS("TICKETS"),
    MORE_INFO("MORE_INFO");

    companion object {
        fun getStringFromEnum(context: Context, enumValue: Enum<*>): String? {
            val resId = context.resources.getIdentifier(enumValue.name, "string", context.packageName)
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