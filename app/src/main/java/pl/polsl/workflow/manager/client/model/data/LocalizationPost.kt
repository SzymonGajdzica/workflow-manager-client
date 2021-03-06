package pl.polsl.workflow.manager.client.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalizationPost(
        val latLng: LatLng,
        val name: String,
        val radius: Double,
): Parcelable