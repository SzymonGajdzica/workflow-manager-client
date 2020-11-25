package pl.polsl.workflow.manager.client.model.data

import kotlinx.parcelize.Parcelize

@Parcelize
data class Localization(
        override val id: Long,
        val name: String,
        val latLng: LatLng,
        val radius: Double,
): Identifiable