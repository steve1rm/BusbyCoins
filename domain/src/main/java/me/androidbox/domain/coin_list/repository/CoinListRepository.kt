package me.androidbox.domain.coin_list.repository

import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

interface CoinListRepository {
    suspend fun fetchCoinList(): CheckResult<CoinListModel, DataError.Network, ErrorModel>
}