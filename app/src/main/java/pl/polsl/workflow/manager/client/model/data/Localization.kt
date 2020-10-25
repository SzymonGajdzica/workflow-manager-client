package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Localization(
        val id: Long,
        val latLng: LatLng,
        val name: String,
        val radius: Double,
): Parcelable