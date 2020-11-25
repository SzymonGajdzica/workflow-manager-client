package pl.polsl.workflow.manager.client.model.data

import java.io.Serializable

sealed class AllowableValue<T: Identifiable>(val id: Long?): Serializable {

    data class NotAllowed<T: Identifiable>(val mId: Long?): AllowableValue<T>(mId)

    data class Allowed<T: Identifiable>(val value: T): AllowableValue<T>(value.id)

}