package me.androidbox.data.coin_list.mappers

import me.androidbox.data.coin_detail.dto.CoinDetailDto
import me.androidbox.data.coin_list.dto.CoinListDto
import me.androidbox.domain.coin_detail.models.CoinDetailModel
import me.androidbox.domain.coin_detail.models.CoinFullDetailModel
import me.androidbox.domain.coin_detail.models.DataDetailModel
import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.coin_list.models.CoinModel
import me.androidbox.domain.coin_list.models.DataModel
import me.androidbox.domain.coin_list.models.StatsModel

fun CoinListDto.toCoinListModel(): CoinListModel {
    return CoinListModel(
        status = this.status,
        data = DataModel(
            stats = StatsModel(
                total = this.data.stats.total,
                totalCoins = this.data.stats.totalCoins,
                total24hVolume = this.data.stats.total24hVolume,
                totalMarkets = this.data.stats.totalMarkets,
                totalExchanges = this.data.stats.totalExchanges,
                totalMarketCap = this.data.stats.totalMarketCap),
            coins = this.data.coins.map { coinDto ->
                CoinModel(
                    listedAt = coinDto.listedAt,
                    name = coinDto.name,
                    rank = coinDto.rank,
                    tier = coinDto.tier,
                    uuid = coinDto.uuid,
                    btcPrice = coinDto.btcPrice,
                    change = coinDto.change,
                    coinrankingUrl = coinDto.coinrankingUrl,
                    color = coinDto.color.orEmpty(),
                    hVolume = coinDto.h24Volume,
                    iconUrl = coinDto.iconUrl,
                    lowVolume = coinDto.lowVolume,
                    contractAddresses = coinDto.contractAddresses.map { it },
                    marketCap = coinDto.marketCap,
                    symbol = coinDto.symbol,
                    price = coinDto.price,
                    sparkline = coinDto.sparkline.mapNotNull { it })
            }
        )
    )
}

fun CoinDetailDto.toCoinDetailModel(): CoinDetailModel {
    return CoinDetailModel(
        status = this.status,
        data = DataDetailModel(
            coin = CoinFullDetailModel(
                name = this.data.coin.name,
                uuid = this.data.coin.uuid,
                btcPrice = this.data.coin.btcPrice,
                change = this.data.coin.change,
                iconUrl = this.data.coin.iconUrl,
                marketCap = this.data.coin.marketCap,
                symbol = this.data.coin.symbol,
                price = this.data.coin.price,
                description = this.data.coin.description,
                websiteUrl = this.data.coin.websiteUrl,
                priceAt = this.data.coin.priceAt)
        )
    )
}