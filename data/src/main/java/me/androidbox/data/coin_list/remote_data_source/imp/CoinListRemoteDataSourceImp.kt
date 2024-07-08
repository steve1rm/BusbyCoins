package me.androidbox.data.coin_list.remote_data_source.imp

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import me.androidbox.data.coin_list.dto.CoinListDto
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.data.remote.Routes
import me.androidbox.data.utils.ErrorDto
import me.androidbox.data.utils.safeApiRequest
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError

class CoinListRemoteDataSourceImp(
    private val httpClient: HttpClient,
) : CoinListRemoteDataSource {
    override suspend fun fetchCoinList(): CheckResult<CoinListDto, DataError.Network, ErrorDto> {
        val safeResult = safeApiRequest<CoinListDto> {
            val response = httpClient
                .get(Routes.COINS)

            response
        }

        return safeResult
    }
}