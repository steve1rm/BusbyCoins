package me.androidbox.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = BusbyGreen,
    background = BusbyBlack,
    surface = BusbyDarkGray,
    secondary = BusbyWhite,
    tertiary = BusbyWhite,
    primaryContainer = BusbyGreen30,
    onPrimary = BusbyBlackLight,
    onBackground = BusbyWhite,
    onSurface = BusbyWhite,
    onSurfaceVariant = BusbyGray,
    error = BusbyDarkRed
)

val LightColorScheme = lightColorScheme(
    primary = BusbyGreenLight,
    background = BusbyBlackLight,
    surface = BusbyDarkGrayLight,
    secondary = BusbyWhiteLight,
    tertiary = BusbyWhiteLight,
    primaryContainer = BusbyGreen30Light,
    onPrimary = BusbyBlack,
    onBackground = BusbyWhiteLight,
    onSurface = BusbyWhiteLight,
    onSurfaceVariant = BusbyGrayLight,
    error = BusbyDarkRed
)

@Composable
fun BusbyCoinsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}