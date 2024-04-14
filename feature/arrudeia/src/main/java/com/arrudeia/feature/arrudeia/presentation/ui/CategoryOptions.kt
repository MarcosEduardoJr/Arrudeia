package com.arrudeia.feature.arrudeia.presentation.ui
import android.content.Context

enum class CategoryOptions(val resourceName: String) {
    CATEGORY_FOOD("CATEGORY_FOOD"),
    CATEGORY_ACCOMMODATION("CATEGORY_ACCOMMODATION"),
    CATEGORY_OUTDOORS("CATEGORY_OUTDOORS"),
    CATEGORY_ENTERTAINMENT("CATEGORY_ENTERTAINMENT"),
    CATEGORY_PURCHASES_OR_SERVICES("CATEGORY_PURCHASES_OR_SERVICES"),
    CATEGORY_PUBLIC_SERVICE("CATEGORY_PUBLIC_SERVICE"),
    CATEGORY_TRANSPORT("CATEGORY_TRANSPORT"),
    CATEGORY_CAR_SERVICES("CATEGORY_CAR_SERVICES");


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
