package me.androidbox.data.coin_list.remote_data_source

import me.androidbox.data.coin_list.dto.CoinListDto
import me.androidbox.data.utils.ErrorDto
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError

interface CoinListRemoteDataSource {
    suspend fun fetchCoinList(): CheckResult<CoinListDto, DataError.Network, ErrorDto>
}