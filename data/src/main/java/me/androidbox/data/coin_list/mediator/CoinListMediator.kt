package me.androidbox.data.coin_list.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.androidbox.data.coin_list.dto.CoinListDto
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.domain.utils.CheckResult

class CoinListMediator(
    private val coinListRemoteDataSource: CoinListRemoteDataSource
) : PagingSource<Int, CoinListDto>() {

    override fun getRefreshKey(state: PagingState<Int, CoinListDto>): Int? {
        val pageKey = state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

        return pageKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinListDto> {
        val pageNumber = params.key ?: 1
        val previousKey = if(pageNumber == 1) null else pageNumber - 1

        when(coinListRemoteDataSource.fetchCoinList()) {
            is CheckResult.Failure -> {

            }
            is CheckResult.Success -> {

            }
        }

        TODO()
    }
}