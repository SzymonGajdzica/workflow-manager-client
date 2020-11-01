package pl.polsl.workflow.manager.client.model.data

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Localization(
        override val id: Long,
        val latLng: LatLng,
        val name: String,
        val radius: Double,
): Identifiable