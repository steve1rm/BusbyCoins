package me.androidbox.domain.coin_detail

import me.androidbox.domain.coin_list.models.DataModel

data class CoinDetailModel(
    val data: DataModel,
    val status: String
)