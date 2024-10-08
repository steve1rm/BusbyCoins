@file:OptIn(FlowPreview::class)

package me.androidbox.presentation.coin_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.insertHeaderItem
import androidx.paging.map
import co.touchlab.kermit.Logger
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.domain.coin_detail.usescases.FetchCoinDetailUseCase
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase
import me.androidbox.domain.scheduler.SyncUpdateCoinsScheduler
import me.androidbox.domain.utils.CheckResult
import me.androidbox.presentation.coin_list.components.InviteFriendCard

class CoinListViewModel(
    private val fetchCoinListUseCase: FetchCoinListUseCase,
    private val fetchCoinDetailUseCase: FetchCoinDetailUseCase,
    private val coinListRepositoryImp: CoinListRepositoryImp,
    private val syncUpdateCoinsScheduler: SyncUpdateCoinsScheduler
) : ViewModel() {

    var coinDetailState by mutableStateOf(CoinListState())
        private set

    var coinTopRankedState by mutableStateOf(listOf(CoinListState()))
        private set

    var coinListLoadingState by mutableStateOf(false)
        private set

    private val _coinListFlow = MutableStateFlow<PagingData<CoinListState>>(PagingData.empty())
    val coinList: StateFlow<PagingData<CoinListState>> = _coinListFlow.asStateFlow()

    init {
        fetchCoinList(limit = 3)
        fetchNewSearchPaging("")

        viewModelScope.launch {
            syncUpdateCoinsScheduler.scheduleUpdate()
        }
    }

    private fun fetchNewSearchPaging(searchTerm: String) {
        viewModelScope.launch {
            coinListRepositoryImp
                .getPagedCoinList(searchTerm)
                .debounce(500)
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
                .collect { pagingData ->
                    val inviteCardSearched = if(
                        "invite".startsWith(searchTerm, ignoreCase = true) && searchTerm.isNotBlank()
                    ) {
                       pagingData.insertHeaderItem(
                            item = CoinListState(
                                itemCardType = CoinListState.ItemCardType.INVITE_FRIEND_CARD
                            )
                        )
                    }
                    else {
                        pagingData
                    }
                    _coinListFlow.value = inviteCardSearched
                }
        }
    }

    /** The number of items you want to return, in this case the top ranked 3 items */
    private fun fetchCoinList(limit: Int) {
        viewModelScope.launch {
            coinListLoadingState = true

            when(val checkResult = fetchCoinListUseCase.execute(offset = 0, limit = limit)) {
                is CheckResult.Failure -> {
                    coinListLoadingState = false
                }
                is CheckResult.Success -> {
                    coinListLoadingState = false

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
                    coinDetailState = coinDetailState.copy(
                        imageUri = "",
                        name = "",
                        symbol = "",
                        price = "",
                        change = "",
                        websiteUrl = "",
                        color = "",
                        marketCap = "",
                        isLoading = false,
                        description = checkResult.exceptionError.toString(),
                        hasError = true)
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
                        isLoading = false,
                        hasError = false
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

            CoinListAction.RetryClicked -> {
                fetchCoinList(limit = 3)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            syncUpdateCoinsScheduler.cancelSync()
        }
    }
}