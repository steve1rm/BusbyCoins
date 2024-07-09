package me.androidbox.data.coin_detail.dto


import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailDto(
    val data: Data,
    val status: String
)