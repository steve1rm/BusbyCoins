package me.androidbox.data.di

import io.ktor.client.HttpClient
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.data.coin_list.remote_data_source.imp.CoinListRemoteDataSourceImp
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.data.network_client.HttpKtorClient
import me.androidbox.domain.coin_list.repository.CoinListRepository
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> { _ ->
       HttpKtorClient()
           .build()
    }

    factory<CoinListRemoteDataSource> {
        CoinListRemoteDataSourceImp(get<HttpClient>())
    }

    factory<CoinListRepository> {
        CoinListRepositoryImp(get<CoinListRemoteDataSource>())
    }
}
