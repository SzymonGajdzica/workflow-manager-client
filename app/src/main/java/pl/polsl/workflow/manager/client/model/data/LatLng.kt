package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatLng(
        val latitude: Double,
        val longitude: Double,
): Parcelable