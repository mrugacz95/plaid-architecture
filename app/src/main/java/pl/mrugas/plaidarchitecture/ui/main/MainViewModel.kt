package pl.mrugas.plaidarchitecture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.mrugas.domain.repos.GetReposUseCase
import pl.mrugas.plaidarchitecture.ui.CoroutinesDispatcherProvider
import pl.mrugas.plaidarchitecture.util.Event
import pl.mrugas.repository.Repo
import pl.mrugas.repository.Result

class MainViewModel(
    private val getReposUseCase: GetReposUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>>
        get() = _repos

    private val _notification = MutableLiveData<Event<String>>()
    val notification: LiveData<Event<String>>
        get() = _notification

    val user: MutableLiveData<String> = MutableLiveData("mrugacz95")

    fun onFetchClick() {
        fetchRepos()
    }

    private fun fetchRepos() {
        viewModelScope.launch(dispatcherProvider.io) {
            val userName = user.value ?: return@launch
            val repos = getReposUseCase(userName)

            updateRepos(repos)
        }
    }

    private fun updateRepos(repositories: Result<List<Repo>>) {
        when (repositories) {
            is Result.Success -> _repos.postValue(repositories.data)
            is Result.Error -> {
                val message = repositories.exception.message ?: return
                _notification.postValue(Event(message))
            }
        }
    }

}