package me.androidbox.data.coin_list.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.coin_list.mappers.toCoinDetailModel
import me.androidbox.data.coin_list.mappers.toCoinListModel
import me.androidbox.data.coin_list.pager.CoinListPager
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.domain.coin_detail.models.CoinDetailModel
import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.coin_list.models.CoinModel
import me.androidbox.domain.repository.CoinListRepository
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

class CoinListRepositoryImp(
    private val coinListRemoteDataSource: CoinListRemoteDataSource,
    private val coinListPager: CoinListPager
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

    override suspend fun fetchCoinDetail(uuid: String): CheckResult<CoinDetailModel, DataError.Network, ErrorModel> {
        return when(val apiResponse = coinListRemoteDataSource.fetchCoinDetail(uuid)) {
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
                   data = apiResponse.data.toCoinDetailModel()
               )
            }
        }
    }

    fun getPagedCoinList(): Flow<PagingData<CoinModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                CoinListPager(coinListRemoteDataSource)
            }
        ).flow
    }
}