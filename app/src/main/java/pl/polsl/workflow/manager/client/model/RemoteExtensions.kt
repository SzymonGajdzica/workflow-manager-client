package pl.polsl.workflow.manager.client.model

import org.json.JSONObject
import pl.polsl.workflow.manager.client.App
import retrofit2.HttpException
import retrofit2.Response

fun <T: Any>Result<T>.toRepositoryResult(): RepositoryResult<T> {
    val data = getOrElse {
        return RepositoryResult.Error(it)
    }
    return RepositoryResult.Success(data)
}

suspend fun <T: Any>safeCall(block: suspend () -> T): RepositoryResult<T> {
    return runCatching { block() }.toRepositoryResult().also {
        when(it) {
            is RepositoryResult.Success -> App.log("Date", it.data)
            is RepositoryResult.Error -> App.log("Error", it.error)
        }
    }
}

fun <T: Any>Response<T>.unwrap(): T {
    return this.body() ?: throw HttpException(this)
}

val Throwable.repositoryMessage: String?
    get() = runCatching {
        (this as HttpException).response()?.errorBody()?.charStream()?.readText()?.let {
            JSONObject(it).getString("message")
        }
    }.getOrNull()
