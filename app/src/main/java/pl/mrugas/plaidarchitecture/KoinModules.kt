package pl.mrugas.plaidarchitecture

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.mrugas.domain.repos.GetReposUseCase
import pl.mrugas.plaidarchitecture.ui.CoroutinesDispatcherProvider
import pl.mrugas.plaidarchitecture.ui.main.MainViewModel
import pl.mrugas.repository.GitHubApiService
import pl.mrugas.repository.data_sources.ReposRemoteDataSource
import pl.mrugas.repository.repositories.ReposRepository


val appModule = module {
    single { androidApplication().getSharedPreferences("default", Context.MODE_PRIVATE) as SharedPreferences }
    single {
        CoroutinesDispatcherProvider()
    }
}

val dataModule = module {
    single { GitHubApiService.create() }
}

val mainModule = module {
    viewModel { MainViewModel(get(), get()) }
}

val reposModule = module {
    factory { GetReposUseCase(get()) }
    factory { ReposRepository(get()) }
    factory { ReposRemoteDataSource(get()) }
}

val appModules = arrayListOf(appModule, dataModule, mainModule, reposModule)