package me.androidbox.data.coin_list.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.androidbox.data.coin_list.mappers.toCoinListModel
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.domain.coin_list.models.CoinModel
import me.androidbox.domain.utils.CheckResult

class CoinListPager(
    private val coinListRemoteDataSource: CoinListRemoteDataSource
) : PagingSource<Int, CoinModel>() {

    override fun getRefreshKey(state: PagingState<Int, CoinModel>): Int? {
        val pageKey = state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

        return pageKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinModel> {
        /** If the key is null we are at the first page (1) */
        val position = params.key ?: 1
        val offset = (position - 1) * 20

       when(val response = coinListRemoteDataSource.fetchCoinList(offset)) {
            is CheckResult.Failure -> {
                return LoadResult.Error(Throwable(message = response.exceptionError.toString()))
            }
            is CheckResult.Success -> {
                val nextKey = if (response.data.data.coins.isEmpty()) {
                    null
                }
                else {
                    position + 1
                }

                val coinModel = response.data.toCoinListModel()
                return LoadResult.Page(
                    data = coinModel.data.coins,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = nextKey
                )
            }
        }
    }
}