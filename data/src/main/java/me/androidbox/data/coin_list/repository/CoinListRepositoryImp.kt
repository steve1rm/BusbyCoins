package me.androidbox.data.coin_list.repository

import me.androidbox.data.coin_list.mappers.toCoinListModel
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.coin_list.repository.CoinListRepository
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

class CoinListRepositoryImp(
    private val coinListRemoteDataSource: CoinListRemoteDataSource
) : CoinListRepository {
    override suspend fun fetchCoinList(): CheckResult<CoinListModel, DataError.Network, ErrorModel> {

        return when(val apiResponse = coinListRemoteDataSource.fetchCoinList()) {
            is CheckResult.Failure -> {
                CheckResult.Failure(
                    exceptionError = apiResponse.exceptionError,
                    responseError = ErrorModel(
                        status = apiResponse.responseError?.status.orEmpty(),
                        type = apiResponse.responseError?.type.orEmpty(),
                        message = apiResponse.responseError?.message.orEmpty())
                )
            }
            is CheckResult.Success -> {
                CheckResult.Success(
                    data = apiResponse.data.toCoinListModel()
                )
            }
        }
    }
}