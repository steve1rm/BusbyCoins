package me.androidbox.presentation.di

import me.androidbox.presentation.coin_list.CoinListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val coinListModel = module {

    viewModelOf(::CoinListViewModel)
}