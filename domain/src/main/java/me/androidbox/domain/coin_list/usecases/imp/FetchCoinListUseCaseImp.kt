package me.androidbox.domain.coin_list.usecases.imp

import me.androidbox.domain.coin_list.models.CoinListModel
import me.androidbox.domain.repository.CoinListRepository
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

class FetchCoinListUseCaseImp(
    private val coinListRepository: CoinListRepository
) : FetchCoinListUseCase {

    override suspend fun execute(offset: Int, limit: Int): CheckResult<CoinListModel, DataError.Network, ErrorModel> {
        return coinListRepository.fetchCoinList(offset, limit)
    }
}