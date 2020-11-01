package pl.polsl.workflow.manager.client.model

sealed class RepositoryResult<T: Any> {
    data class Success<T: Any>(val data: T): RepositoryResult<T>()
    data class Error<T: Any>(val error: Throwable): RepositoryResult<T>()
}