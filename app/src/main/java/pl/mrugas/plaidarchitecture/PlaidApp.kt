package pl.mrugas.plaidarchitecture

import android.app.Application
import org.koin.core.context.startKoin

class PlaidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            appModule
        }
    }
}