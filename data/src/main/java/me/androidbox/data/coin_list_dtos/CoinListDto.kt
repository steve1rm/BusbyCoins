package me.androidbox.data.coin_list_dtos


import kotlinx.serialization.Serializable

@Serializable
data class CoinListDto(
    val data: DataDto,
    val status: String
)