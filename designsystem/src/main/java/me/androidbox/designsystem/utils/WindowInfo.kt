package me.androidbox.designsystem.utils

import androidx.compose.ui.unit.Dp

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed interface WindowType {
        data object Compact : WindowType
        data object Medium : WindowType
        data object Expanded : WindowType
    }
}
