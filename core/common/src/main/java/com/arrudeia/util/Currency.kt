package com.arrudeia.util

fun Int.toCurrencyReal() = "R$ %.0f".format(this / 100.0)