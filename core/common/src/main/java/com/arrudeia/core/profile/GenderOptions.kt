package com.arrudeia.core.profile

import android.content.Context
import android.content.res.Resources

enum class GenderOptions(val resourceName: String) {
    ic_gender_male("ic_gender_male"),
    ic_gender_female("ic_gender_female"),
    ic_gender_lgbt("ic_gender_lgbt");


    companion object {


        fun getEnumFromDrawableName(context: Context, drawableName: String): GenderOptions? {
            return values().find { it.getDrawableName(context) == drawableName }
        }

        fun getDrawableFromEnum(context: Context, enumValue: GenderOptions): Int {
            return context.resources.getIdentifier(
                enumValue.resourceName,
                "drawable",
                context.packageName
            )
        }

        fun getAllEnumDrawableReferences(context: Context): List<Int> {
            return values().toList().map { getDrawableFromEnum(context, it) }
        }

        fun getStringFromDrawable(context: Context, drawableResId: Int): String? {
            return try {
                context.resources.getResourceEntryName(drawableResId)
            } catch (e: Resources.NotFoundException) {
                null
            }
        }
    }

    fun getDrawableName(context: Context): String {
        val resId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        return if (resId != 0) {
            context.resources.getResourceEntryName(resId)
        } else {
            ""
        }
    }
}
