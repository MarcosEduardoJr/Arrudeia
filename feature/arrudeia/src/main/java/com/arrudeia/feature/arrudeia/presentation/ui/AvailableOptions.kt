package com.arrudeia.feature.arrudeia.presentation.ui
import android.content.Context

enum class AvailableOptions(val resourceName: String) {
    AVAILABLE_AIR_CONDITIONING("AVAILABLE_AIR_CONDITIONING"),
    AVAILABLE_ACCEPTS_CREDIT_CARD("AVAILABLE_ACCEPTS_CREDIT_CARD"),
    AVAILABLE_PIX("AVAILABLE_PIX"),
    AVAILABLE_MONEY("AVAILABLE_MONEY"),
    AVAILABLE_CURBSIDE_PICKUP("AVAILABLE_CURBSIDE_PICKUP"),
    AVAILABLE_DELIVERY("AVAILABLE_DELIVERY"),
    AVAILABLE_DRIVE_THRU("AVAILABLE_DRIVE_THRU"),
    AVAILABLE_OUTDOOR_SEATING("AVAILABLE_OUTDOOR_SEATING"),
    AVAILABLE_CUSTOMER_PARKING("AVAILABLE_CUSTOMER_PARKING"),
    AVAILABLE_RESERVATIONS("AVAILABLE_RESERVATIONS"),
    AVAILABLE_TAKE_OUT("AVAILABLE_TAKE_OUT"),
    AVAILABLE_VALET_SERVICE("AVAILABLE_VALET_SERVICE"),
    AVAILABLE_WHEELCHAIR_ACCESSIBLE("AVAILABLE_WHEELCHAIR_ACCESSIBLE"),
    AVAILABLE_WI_FI("AVAILABLE_WI_FI");

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
