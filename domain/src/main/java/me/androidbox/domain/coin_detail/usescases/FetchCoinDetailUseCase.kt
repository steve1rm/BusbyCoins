package me.androidbox.domain.coin_detail.usescases

import me.androidbox.domain.coin_detail.models.CoinDetailModel
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

fun interface FetchCoinDetailUseCase {
    suspend fun execute(uuid: String): CheckResult<CoinDetailModel, DataError.Network, ErrorModel>
}