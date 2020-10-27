package pl.polsl.workflow.manager.client

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.abs

fun Instant.toHoursMinutesSeconds(): String {
    val prefix = if(toEpochMilli() < 0L) "-" else ""
    val millis = abs(toEpochMilli())
    val seconds = (millis / 1000L) % 60L
    val minutes = (millis / (1000L * 60L) % 60L)
    val hours = (millis / (1000L * 60L * 60L) % 24L)
    return String.format("$prefix%02d:%02d:%02d", hours, minutes, seconds)
}

fun Instant.formatDate(): String {
    return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())
        .format(this)
}



