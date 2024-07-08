package me.androidbox.data.di

import io.ktor.client.HttpClient
import me.androidbox.data.network_client.HttpKtorClient
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> { _ ->
       HttpKtorClient()
           .build()
    }
}