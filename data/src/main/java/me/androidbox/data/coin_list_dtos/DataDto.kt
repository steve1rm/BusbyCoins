package me.androidbox.data.coin_list_dtos


import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
    val coins: List<CoinDto>,
    val stats: StatsDto
)