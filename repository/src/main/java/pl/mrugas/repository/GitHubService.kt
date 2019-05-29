package pl.mrugas.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

interface GitHubApiService {

    companion object {
        fun create(): GitHubApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("https://api.github.com/")
                .build()

            return retrofit.create(GitHubApiService::class.java)
        }
    }

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Deferred<Response<List<Repo>>>

}

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Result<T>,
    message: String
): Result<T> =
    try {
        call()
    } catch (e: Exception) {
        Result.Error(IOException(message, e))
    }
