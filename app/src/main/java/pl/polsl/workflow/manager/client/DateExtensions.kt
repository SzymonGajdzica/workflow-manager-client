package pl.polsl.workflow.manager.client

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.abs

fun Instant.toHoursMinutesSeconds(): String {
    val prefix = if(toEpochMilli() < 0L) "-" else ""
    val time = Instant.ofEpochMilli(abs(toEpochMilli())).atZone(ZoneOffset.UTC).toLocalTime()
    return String.format("$prefix%02d:%02d:%02d", time.hour, time.minute, time.second)
}

fun Instant.formatDate(): String {
    return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())
        .format(this)
}



