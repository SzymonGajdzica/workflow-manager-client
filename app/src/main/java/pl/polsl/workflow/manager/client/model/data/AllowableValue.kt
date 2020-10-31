package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class AllowableValue<T: Identifiable>(val id: Long?): Parcelable {

    @Parcelize
    data class NotAllowed<T: Identifiable>(val mId: Long?): AllowableValue<T>(mId)
    @Parcelize
    data class Allowed<T: Identifiable>(val value: T): AllowableValue<T>(value.id)

}