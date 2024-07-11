package me.androidbox.data.coin_list.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val btcPrice: String = "",
    val change: String = "",
    val coinrankingUrl: String = "",
    val color: String? = "",
    val contractAddresses: List<String> = emptyList(),
    @SerialName("24hVolume")
    val h24Volume: String = "",
    val iconUrl: String = "",
    val listedAt: Int = 0,
    val lowVolume: Boolean = false,
    val marketCap: String = "",
    val name: String = "",
    val price: String = "",
    val rank: Int = 0,
    val sparkline: List<String?> = emptyList(),
    val symbol: String = "",
    val tier: Int = 0,
    val uuid: String = ""
)