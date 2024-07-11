package me.androidbox.presentation.coin_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.domain.coin_detail.usescases.FetchCoinDetailUseCase
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase
import me.androidbox.domain.utils.CheckResult

class CoinListViewModel(
    private val fetchCoinListUseCase: FetchCoinListUseCase,
    private val fetchCoinDetailUseCase: FetchCoinDetailUseCase,
    private val coinListRepositoryImp: CoinListRepositoryImp
) : ViewModel() {

    var coinDetailState by mutableStateOf(CoinListState())
        private set

    var coinTopRankedState by mutableStateOf(listOf(CoinListState()))
        private set

    private val _coinListFlow = MutableStateFlow<PagingData<CoinListState>>(PagingData.empty())
    val coinList: StateFlow<PagingData<CoinListState>> = _coinListFlow

    init {
        fetchCoinList(limit = 3)
        fetchNewSearchPaging("")
    }

    private fun fetchNewSearchPaging(searchTerm: String) {
        viewModelScope.launch {
            coinListRepositoryImp
                .getPagedCoinList(searchTerm)
                .map { pagingData ->
                    pagingData
                        .filter { coinModel ->
                            /** Filter out the first 3 top rankings */
                            coinModel.rank > 3
                        }
                        .map { coinModel ->
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
                .collect {
                    _coinListFlow.value = it
                }
        }
    }

    /** The number of items you want to return, in this case the top ranked 3 items */
    private fun fetchCoinList(limit: Int) {
        viewModelScope.launch {
            coinDetailState = coinDetailState.copy(isLoading = true)

            when(val checkResult = fetchCoinListUseCase.execute(offset = 0, limit = limit)) {
                is CheckResult.Failure -> {
                    coinDetailState = coinDetailState.copy(isLoading = false)
                }
                is CheckResult.Success -> {
                    coinDetailState = coinDetailState.copy(isLoading = false)

                    val listOfCoins = checkResult.data.data.coins.map { coinModel ->
                        CoinListState(
                            imageUri = coinModel.iconUrl,
                            name = coinModel.name,
                            symbol = coinModel.symbol,
                            change = coinModel.change
                        )
                    }
                    coinTopRankedState = listOfCoins
                    Logger.d {
                        "${coinTopRankedState.count()}"
                    }
                }
            }
        }
    }

    private fun fetchCoinDetail(uuid: String) {
        viewModelScope.launch {
            coinDetailState = coinDetailState.copy(isLoading = true)

            when(val checkResult = fetchCoinDetailUseCase.execute(uuid = uuid)) {
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
                        color = checkResult.data.data.coin.color,
                        marketCap = checkResult.data.data.coin.marketCap,
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

            is CoinListAction.SearchTermInput -> {
                fetchNewSearchPaging(action.searchTerm)
            }
        }
    }
}