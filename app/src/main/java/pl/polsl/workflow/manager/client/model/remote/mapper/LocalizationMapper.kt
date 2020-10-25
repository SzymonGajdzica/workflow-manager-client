package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.LatLng
import pl.polsl.workflow.manager.client.model.data.Localization
import pl.polsl.workflow.manager.client.model.data.LocalizationPost
import pl.polsl.workflow.manager.client.model.remote.data.LatLngApiModel
import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModel
import pl.polsl.workflow.manager.client.model.remote.data.LocalizationApiModelPost

fun LocalizationApiModel.map(): Localization {
    return Localization(
        id = id,
        name = name,
        latLng = latLngModel.map(),
        radius = radius
    )
}

fun LatLngApiModel.map(): LatLng {
    return LatLng(
        latitude = latitude,
        longitude = longitude
    )
}

fun LatLng.map(): LatLngApiModel {
    return LatLngApiModel(
        latitude = latitude,
        longitude = longitude
    )
}

fun LocalizationPost.map(): LocalizationApiModelPost {
    return LocalizationApiModelPost(
        latLngModel = latLng.map(),
        name = name,
        radius = radius
    )
}