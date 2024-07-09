package me.androidbox.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase

class CoinListViewModel(
    private val fetchCoinListUseCase: FetchCoinListUseCase,
    coinListRepositoryImp: CoinListRepositoryImp
) : ViewModel() {

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
}