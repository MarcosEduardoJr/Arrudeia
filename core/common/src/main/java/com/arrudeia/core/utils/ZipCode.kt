package com.arrudeia.core.utils



fun String.isValidZipCode(): Boolean {
    val allowedCharacters = Regex("^[a-zA-Z0-9- ]*$")
    val minLength = 3
    val maxLength = 10

    return allowedCharacters.matches(this) && this.length in minLength..maxLength
}