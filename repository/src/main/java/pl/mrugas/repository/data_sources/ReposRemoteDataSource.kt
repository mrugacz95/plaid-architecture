package pl.mrugas.repository.data_sources

import pl.mrugas.repository.GitHubApiService
import pl.mrugas.repository.Repo
import pl.mrugas.repository.Result
import pl.mrugas.repository.safeApiCall
import java.io.IOException

class ReposRemoteDataSource(private val service: GitHubApiService) {
    suspend fun requestGetRepos(user: String): Result<List<Repo>> = safeApiCall(
        call = {
            getRepos(user)
        },
        message = "Unable to fetch repos"
    )

    private suspend fun getRepos(user: String): Result<List<Repo>> {
        val response = service.getRepos(user).await()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.isNotEmpty()) {
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error fetching repos: ${response.message()}"))
    }
}