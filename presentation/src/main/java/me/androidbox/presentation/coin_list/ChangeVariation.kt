package me.androidbox.presentation.coin_list

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import me.androidbox.presentation.R

enum class ChangeVariation(
    val change: Int,
    @DrawableRes arrow: Int
) {
    UP(change = 1,
        arrow = R.drawable.arrow_up
    ),
    DOWN(
        change = 1,
        arrow = R.drawable.arrow_down
    )
}