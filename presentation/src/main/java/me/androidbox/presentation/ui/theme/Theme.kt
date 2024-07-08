package me.androidbox.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = BusbyGreen,
    background = BusbyBlack,
    surface = BusbyDarkGray,
    secondary = BusbyWhite,
    tertiary = BusbyWhite,
    primaryContainer = BusbyGreen30,
    onPrimary = BusbyBlack,
    onBackground = BusbyWhite,
    onSurface = BusbyWhite,
    onSurfaceVariant = BusbyGray,
    error = BusbyDarkRed
)

val LightColorScheme = darkColorScheme(
    primary = BusbyGreenLight,
    background = BusbyBlackLight,
    surface = BusbyDarkGrayLight,
    secondary = BusbyWhiteLight,
    tertiary = BusbyWhiteLight,
    primaryContainer = BusbyGreen30Light,
    onPrimary = BusbyBlackLight,
    onBackground = BusbyWhiteLight,
    onSurface = BusbyWhiteLight,
    onSurfaceVariant = BusbyGrayLight,
    error = BusbyDarkRed
)

@Composable
fun BusbyCoinsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}