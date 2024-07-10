package me.androidbox.presentation.utils

import androidx.compose.ui.graphics.Color
import java.text.DecimalFormat

fun String.toFormattedPrice(): String {
    val formatter = DecimalFormat("#,##0.00000")

    return this.toDoubleOrNull()?.let { price ->
        return formatter.format(price)
    } ?: this
}


fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor("#ff$this"))
}