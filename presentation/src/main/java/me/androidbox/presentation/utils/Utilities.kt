package me.androidbox.presentation.utils

import java.text.DecimalFormat
import androidx.compose.ui.graphics.Color
import co.touchlab.kermit.Logger

fun String.toFormattedPrice(decimalPlaces: Int = 5): String {

    val formatter = when(decimalPlaces) {
        1 -> DecimalFormat("#,##0.0")
        2 -> DecimalFormat("#,##0.00")
        3 -> DecimalFormat("#,##0.000")
        4 -> DecimalFormat("#,##0.0000")
        5 -> DecimalFormat("#,##0.00000")
        else -> DecimalFormat("#,##0.000000")
    }

    return this.toDoubleOrNull()?.let { price ->
        return formatter.format(price)
    } ?: this
}

fun String.appendWithSuffix(): String {
    val number = this.toDoubleOrNull()

    return if(number != null) {
        when {
            number >= 1_000_000_000_000 -> String.format("%.2f trillion", number / 1_000_000_000_000)
            number >= 1_000_000_000 -> String.format("%.2f billion", number / 1_000_000_000)
            number >= 1_000_000 -> String.format("%.2f million", number / 1_000_000)
            else -> number.toString()
        }
    }
    else {
        ""
    }
}

fun parseColor(colorString: String): Color {
    val color = try {
        // Convert the color string to a Color object
        val colorInt = colorString.substring(1).toInt(16)

        Color(colorInt or 0xFF000000.toInt())
    }
    catch (ex: Exception) {
        Logger.d(throwable = ex, message = { ex.message ?: "" })
        Color(0xFF000000.toInt())
    }

    return color
}
