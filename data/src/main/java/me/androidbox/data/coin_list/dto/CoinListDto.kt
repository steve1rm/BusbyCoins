package me.androidbox.data.coin_list.dto


import kotlinx.serialization.Serializable

@Serializable
data class CoinListDto(
    val data: DataDto,
    val status: String
)