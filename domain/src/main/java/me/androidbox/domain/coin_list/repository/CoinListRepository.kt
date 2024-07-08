package me.androidbox.domain.coin_list.repository

interface CoinListRepository {
    suspend fun fetchCoinList()
}