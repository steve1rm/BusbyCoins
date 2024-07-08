package me.androidbox.data.remote

import me.androidbox.data.BuildConfig

object Routes {
    private const val BASE_URL = BuildConfig.BASE_COIN_RANKING_ENDPOINT
    const val COINS = "$BASE_URL/coins"
}