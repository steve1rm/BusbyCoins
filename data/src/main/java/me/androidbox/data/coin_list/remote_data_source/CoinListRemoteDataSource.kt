package me.androidbox.data.coin_list.remote_data_source

import me.androidbox.data.coin_list.dto.CoinListDto
import me.androidbox.data.utils.ErrorDto
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError

interface CoinListRemoteDataSource {
    suspend fun fetchCoinList(offset: Int = 10, limit: Int = 20): CheckResult<CoinListDto, DataError.Network, ErrorDto>
}