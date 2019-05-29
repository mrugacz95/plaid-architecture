package pl.mrugas.repository.repositories

import pl.mrugas.repository.Repo
import pl.mrugas.repository.Result
import pl.mrugas.repository.data_sources.ReposRemoteDataSource

class ReposRepository(private val remoteDataSource: ReposRemoteDataSource) {
    suspend fun loadRepos(user: String): Result<List<Repo>> {
        if (cache.containsKey(user)) {
            val repos = cache[user] ?: return Result.Error(IllegalStateException("Repo cache failed for user $user"))
            return Result.Success(repos)
        }
        return getData(user, request = { remoteDataSource.requestGetRepos(user) })
    }

    val cache = mutableMapOf<String, List<Repo>>()
    suspend fun getData(user: String, request: suspend () -> Result<List<Repo>>): Result<List<Repo>> {
        val result = request()
        if (result is Result.Success) {
            cache[user] = result.data
        }
        return result
    }
}