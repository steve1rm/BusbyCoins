package me.androidbox.domain.coin_detail.usescases.imp

import me.androidbox.domain.coin_detail.models.CoinDetailModel
import me.androidbox.domain.coin_detail.usescases.FetchCoinDetailUseCase
import me.androidbox.domain.repository.CoinListRepository
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError
import me.androidbox.domain.utils.ErrorModel

class FetchCoinDetailUseCaseImp(
    private val coinListRepository: CoinListRepository
) : FetchCoinDetailUseCase {

    override suspend fun execute(uuid: String): CheckResult<CoinDetailModel, DataError.Network, ErrorModel> {
        return coinListRepository.fetchCoinDetail(uuid = uuid)
    }
}