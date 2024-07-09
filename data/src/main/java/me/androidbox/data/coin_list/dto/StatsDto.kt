package me.androidbox.data.coin_list.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatsDto(
    val total: Int,
    val total24hVolume: String,
    val totalCoins: Int,
    val totalExchanges: Int,
    val totalMarketCap: String,
    val totalMarkets: Int
)