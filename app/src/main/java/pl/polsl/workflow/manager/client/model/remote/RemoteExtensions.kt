package pl.polsl.workflow.manager.client.model.remote

fun <T: Any>Result<T>.toRepositoryResult(): RepositoryResult<T> {
    val data = getOrElse {
        return RepositoryResult.Error(it)
    }
    return RepositoryResult.Success(data)
}
