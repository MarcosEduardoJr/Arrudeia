package com.arrudeia.feature.checklist.presentation.ui

import android.content.Context

enum class ChecklistNameEnum(val resourceName: String) {
    DOCUMENT("DOCUMENT"),
    VISA("VISA"),
    MONEY("MONEY"),
    CARD_PAYMENT("CARD_PAYMENT"),
    TICKET_TRANSPORT("TICKET_TRANSPORT"),
    HOTEL_RESERVATION_CONFIRMATION("HOTEL_RESERVATION_CONFIRMATION"),
    TRAVEL_INSURANCE("TRAVEL_INSURANCE"),
    PERSONAL_MEDICATIONS("PERSONAL_MEDICATIONS"),
    SUN_GLASSES("SUN_GLASSES"),
    SUN_PROTECTION("SUN_PROTECTION"),
    HAT("HAT"),
    PERSONAL_HYGINE_ITEMS("PERSONAL_HYGINE_ITEMS"),
    CAMERA_PHOTO("CAMERA_PHOTO"),
    CHARGERS_AND_PLUG("CHARGERS_AND_PLUG"),
    CLOTHES("CLOTHES"),
    SHOES("SHOES"),
    BAG("BAG"),
    SNACKS("SNACKS"),
    HEADSET("HEADSET"),
    DRIVER_LICENSE("DRIVER_LICENSE"),
    MASK_AND_ALCOOL("MASK_AND_ALCOOL"),
    NECK_PILLOW("NECK_PILLOW");

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
