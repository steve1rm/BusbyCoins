package me.androidbox.presentation.utils

import java.text.DecimalFormat

fun String.toFormattedPrice(): String {
    val formatter = DecimalFormat("#,##0.000000")

    return this.toDoubleOrNull()?.let { price ->
        return formatter.format(price)
    } ?: this
}