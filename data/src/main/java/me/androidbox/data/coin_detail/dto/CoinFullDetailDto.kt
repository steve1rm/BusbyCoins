package me.androidbox.data.coin_detail.dto


import kotlinx.serialization.Serializable

@Serializable
data class CoinFullDetailDto(
    val btcPrice: String,
    val change: String,
    val description: String,
    val iconUrl: String,
    val marketCap: String,
    val name: String,
    val price: String,
    val priceAt: Int,
    val symbol: String,
    val uuid: String,
    val websiteUrl: String,
    val color: String
)