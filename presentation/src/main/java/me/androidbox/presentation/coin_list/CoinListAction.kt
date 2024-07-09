package me.androidbox.presentation.coin_list

sealed interface CoinListAction {
    data class CoinListCardClicked(val uuid: String) : CoinListAction
}