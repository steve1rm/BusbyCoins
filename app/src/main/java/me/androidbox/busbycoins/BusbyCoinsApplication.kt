package me.androidbox.busbycoins

import android.app.Application
import me.androidbox.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BusbyCoinsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BusbyCoinsApplication)
            modules(
                networkModule,
            )
        }
    }
}