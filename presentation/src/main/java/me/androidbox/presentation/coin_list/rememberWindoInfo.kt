package me.androidbox.presentation.coin_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current

    val screenWidthInfo = when {
        configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
        configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
        else -> WindowInfo.WindowType.Expanded
    }

    val screenHeightInfo = when {
        configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compact
        configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
        else -> WindowInfo.WindowType.Expanded
    }

    return WindowInfo(
        screenWidthInfo = screenWidthInfo,
        screenHeightInfo = screenHeightInfo,
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}