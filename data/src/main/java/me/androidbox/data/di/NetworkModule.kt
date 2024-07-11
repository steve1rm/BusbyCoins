package me.androidbox.data.di

import io.ktor.client.HttpClient
import me.androidbox.data.coin_list.pager.CoinListPager
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.data.coin_list.remote_data_source.imp.CoinListRemoteDataSourceImp
import me.androidbox.data.coin_list.repository.CoinListRepositoryImp
import me.androidbox.data.network_client.HttpKtorClient
import me.androidbox.data.scheduler.SyncUpdateCoinsSchedulerImp
import me.androidbox.data.worker.UpdateCoinsWorker
import me.androidbox.domain.repository.CoinListRepository
import me.androidbox.domain.scheduler.SyncUpdateCoinsScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> { _ ->
        HttpKtorClient()
            .build()
    }

    factory<CoinListRemoteDataSource> {
        CoinListRemoteDataSourceImp(get<HttpClient>())
    }

    factoryOf(::CoinListPager)

    factory<CoinListRepository> {
        CoinListRepositoryImp(get<CoinListRemoteDataSource>())
    }

    factory<CoinListRepositoryImp> {
        CoinListRepositoryImp(get<CoinListRemoteDataSource>())
    }

    workerOf(::UpdateCoinsWorker)

    factory<SyncUpdateCoinsScheduler> {
        SyncUpdateCoinsSchedulerImp(androidContext())
    }
}
