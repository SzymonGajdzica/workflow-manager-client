package pl.polsl.workflow.manager.client.model.remote

import org.json.JSONObject
import pl.polsl.workflow.manager.client.App
import retrofit2.HttpException

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

val Throwable.repositoryMessage: String?
    get() = runCatching {
        (this as HttpException).response()?.errorBody()?.charStream()?.readText()?.let {
            App.log(it)
            JSONObject(it).getString("message")
        }
    }.getOrNull()
