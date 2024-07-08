package me.androidbox.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase

class CoinListViewModel(
    private val fetchCoinListUseCase: FetchCoinListUseCase
) : ViewModel() {

    init {
        fetchCoinList()
    }

    fun fetchCoinList() {
        viewModelScope.launch {
            fetchCoinListUseCase.execute()
        }
    }
}