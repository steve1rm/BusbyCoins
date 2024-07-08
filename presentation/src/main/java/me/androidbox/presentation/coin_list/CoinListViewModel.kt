package me.androidbox.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.domain.coin_list.models.CoinModel
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase

class CoinListViewModel(
    private val fetchCoinListUseCase: FetchCoinListUseCase,
    private val coinListRepositoryImp: CoinListRepositoryImp
) : ViewModel() {

    val coinList = coinListRepositoryImp
        .getPagedCoinList()
        .cachedIn(viewModelScope)

    init {
       // fetchCoinList()
    }

    fun fetchCoinList() {
        viewModelScope.launch {
            fetchCoinListUseCase.execute()
        }
    }
}