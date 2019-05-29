package pl.mrugas.domain.repos

import pl.mrugas.repository.Repo
import pl.mrugas.repository.Result
import pl.mrugas.repository.repositories.ReposRepository

class GetReposUseCase(private val reposRepository: ReposRepository) {
    suspend operator fun invoke(user: String): Result<List<Repo>> {
        val result = reposRepository.loadRepos(user)
        return if (result is Result.Success) {
            result
        } else {
            Result.Error(IllegalStateException("$user's repos not cached"))
        }
    }
}