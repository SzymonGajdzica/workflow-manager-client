package pl.polsl.workflow.manager.client.model.remote.data

import com.google.gson.annotations.SerializedName

data class LocalizationApiModel(
        @SerializedName("id")
        override val id: Long,
        @SerializedName("latLng")
        val latLngModel: LatLngApiModel,
        @SerializedName("name")
        val name: String,
        @SerializedName("radius")
        val radius: Double,
): IdentifiableApiModel