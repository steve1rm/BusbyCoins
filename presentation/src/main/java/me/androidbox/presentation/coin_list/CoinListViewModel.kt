package me.androidbox.presentation.coin_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.domain.coin_detail.usescases.FetchCoinDetailUseCase
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase
import me.androidbox.domain.utils.CheckResult

class CoinListViewModel(
    private val fetchCoinListUseCase: FetchCoinListUseCase,
    private val fetchCoinDetailUseCase: FetchCoinDetailUseCase,
    coinListRepositoryImp: CoinListRepositoryImp
) : ViewModel() {

    var coinDetailState by mutableStateOf(CoinListState())
        private set

    val coinList = coinListRepositoryImp
        .getPagedCoinList()
        .map { pagingData ->
            pagingData.map { coinModel ->
                CoinListState(
                    imageUri = coinModel.iconUrl,
                    name = coinModel.name,
                    symbol = coinModel.symbol,
                    price = coinModel.price,
                    change = coinModel.change,
                    uuid = coinModel.uuid
                )
            }
        }
        .cachedIn(viewModelScope)

    fun fetchCoinList() {
        viewModelScope.launch {
            fetchCoinListUseCase.execute()
        }
    }

    private fun fetchCoinDetail(uuid: String) {
        viewModelScope.launch {
            coinDetailState = coinDetailState.copy(isLoading = true)

            val checkResult = fetchCoinDetailUseCase.execute(uuid = uuid)

            when(checkResult) {
                is CheckResult.Failure -> {
                    coinDetailState = coinDetailState.copy(isLoading = true)
                    /** Show error */
                }
                is CheckResult.Success -> {
                    coinDetailState = coinDetailState.copy(
                        imageUri = checkResult.data.data.coin.iconUrl,
                        name = checkResult.data.data.coin.name,
                        symbol = checkResult.data.data.coin.symbol,
                        price = checkResult.data.data.coin.price,
                        change = checkResult.data.data.coin.change,
                        description = checkResult.data.data.coin.description,
                        websiteUrl = checkResult.data.data.coin.websiteUrl,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun coinListAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.CoinListCardClicked -> {
                fetchCoinDetail(action.uuid)
            }
        }
    }
}