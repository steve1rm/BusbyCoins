package me.androidbox.domain.di

import me.androidbox.domain.coin_list.repository.CoinListRepository
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase
import me.androidbox.domain.coin_list.usecases.imp.FetchCoinListUseCaseImp
import org.koin.dsl.module

val useCaseModule = module {

    factory<FetchCoinListUseCase> {
        FetchCoinListUseCaseImp(get<CoinListRepository>())
    }
}