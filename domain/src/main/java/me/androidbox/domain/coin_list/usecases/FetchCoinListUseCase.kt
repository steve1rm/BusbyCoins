package me.androidbox.domain.coin_list.usecases

import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

interface FetchCoinListUseCase {
    suspend fun execute(offset: Int = 10, limit: Int = 20): CheckResult<CoinListModel, DataError.Network, ErrorModel>
}