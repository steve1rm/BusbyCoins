package me.androidbox.data.coin_detail.dto


import kotlinx.serialization.Serializable

@Serializable
data class DataDetailDto(
    val coin: CoinFullDetailDto
)