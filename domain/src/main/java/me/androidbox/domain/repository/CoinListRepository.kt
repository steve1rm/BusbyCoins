package me.androidbox.domain.repository

import me.androidbox.domain.coin_detail.CoinDetailModel
import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

interface CoinListRepository {
    suspend fun fetchCoinList(): CheckResult<CoinListModel, DataError.Network, ErrorModel>
    suspend fun fetchCoinDetail(): CheckResult<CoinDetailModel, DataError.Network, ErrorModel>
}