package com.arrudeia.feature.services.presentation.ui

import android.content.Context

enum class EnumServiceExpertise(val resourceName: String) {
    TECHNOLOGY("TECHNOLOGY"),
    DOMESTIC("DOMESTIC"),
    TECHNICAL_ASSISTANCE("TECHNICAL_ASSISTANCE"),
    HEALTH_AND_WELLNESS("HEALTH_AND_WELLNESS"),
    EDUCATIONAL("EDUCATIONAL"),
    TRANSPORTATION("TRANSPORTATION"),
    VEHICLE_MAINTENANCE("VEHICLE_MAINTENANCE"),
    CONSULTING("CONSULTING"),
    ADMINISTRATIVE("ADMINISTRATIVE"),
    ENTERTAINMENT_AND_EVENTS("ENTERTAINMENT_AND_EVENTS"),
    CONSTRUCTION_AND_RENOVATION("CONSTRUCTION_AND_RENOVATION"),
    LEGAL("LEGAL"),
    BEAUTY_AND_AESTHETICS("BEAUTY_AND_AESTHETICS"),
    GUIDE("GUIDE");


    companion object {

        fun getEnumValue(value: String): String {
            val result = entries.firstOrNull { it.name == value }
            return result?.name ?: value
        }

        fun getStringFromEnum(context: Context, enumValue: String): String {
            val resId =
                context.resources.getIdentifier(enumValue, "string", context.packageName)
            return if (resId != 0) {
                context.getString(resId)
            } else {
                enumValue
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
