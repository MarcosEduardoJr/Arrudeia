package com.arrudeia.core.navigation

import androidx.navigation.NavController

object NavControllerHolder {
    lateinit var navController: NavController
}

fun navigateParam(param: Any) {
    NavControllerHolder.navController.navigate(param)
}