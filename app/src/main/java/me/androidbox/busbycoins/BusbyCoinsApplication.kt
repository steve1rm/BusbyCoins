package me.androidbox.busbycoins

import android.app.Application
import me.androidbox.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BusbyCoinsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@BusbyCoinsApplication)
            modules(
                networkModule,
            )
        }
    }
}