package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable

interface Identifiable: Parcelable {
    val id: Long
}