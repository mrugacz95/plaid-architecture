package pl.mrugas.plaidarchitecture

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appModule = module {
    single { androidApplication().getSharedPreferences("default", Context.MODE_PRIVATE) as SharedPreferences }
}