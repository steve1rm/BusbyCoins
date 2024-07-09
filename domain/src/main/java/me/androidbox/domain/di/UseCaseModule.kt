package me.androidbox.domain.di

import me.androidbox.domain.coin_detail.usescases.FetchCoinDetailUseCase
import me.androidbox.domain.coin_detail.usescases.imp.FetchCoinDetailUseCaseImp
import me.androidbox.domain.repository.CoinListRepository
import me.androidbox.domain.coin_list.usecases.FetchCoinListUseCase
import me.androidbox.domain.coin_list.usecases.imp.FetchCoinListUseCaseImp
import org.koin.dsl.module

val useCaseModule = module {

    factory<FetchCoinListUseCase> {
        FetchCoinListUseCaseImp(get<CoinListRepository>())
    }

    factory<FetchCoinDetailUseCase> {
        FetchCoinDetailUseCaseImp(get<CoinListRepository>())
    }
}