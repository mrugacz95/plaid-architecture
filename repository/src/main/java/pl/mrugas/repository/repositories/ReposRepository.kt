package pl.mrugas.repository.repositories

import pl.mrugas.repository.Repo
import pl.mrugas.repository.Result
import pl.mrugas.repository.data_sources.ReposRemoteDataSource

class ReposRepository(val remoteDataSource: ReposRemoteDataSource) {
    suspend fun loadRepos(user: String) {
        getData(user, request = { remoteDataSource.requestGetRepos(user) })
    }

    val cache = mutableMapOf<String, List<Repo>>()
    suspend fun getData(user: String, request: suspend () -> Result<List<Repo>>): Result<List<Repo>> {
        val result = request()
        if (result is Result.Success) {
            cache[user] = result.data
        }
        return result
    }

    companion object {
        @Volatile
        private var INSTANCE: ReposRepository? = null

        fun getInstance(remoteDataSource: ReposRemoteDataSource): ReposRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ReposRepository(remoteDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}