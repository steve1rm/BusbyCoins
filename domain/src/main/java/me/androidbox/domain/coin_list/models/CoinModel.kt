package me.androidbox.domain.coin_list.models


data class CoinModel(
    val btcPrice: String,
    val change: String,
    val coinrankingUrl: String,
    val color: String,
    val contractAddresses: List<String>,
    val hVolume: String,
    val iconUrl: String,
    val listedAt: Int,
    val lowVolume: Boolean,
    val marketCap: String,
    val name: String,
    val price: String,
    val rank: Int,
    val sparkline: List<String>,
    val symbol: String,
    val tier: Int,
    val uuid: String
)