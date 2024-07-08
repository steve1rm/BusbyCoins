package me.androidbox.presentation.coin_list

data class CoinListState(
    val imageUri: String,
    val name: String,
    val symbol: String,
    val price: String,
    val change: String
)
