package io.afalabarce.template.kmm.core.common.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.*

actual fun Int.format(format: String): String = try {
    DecimalFormat(format).format(this)
} catch (_: Exception) {
    ""
}

actual fun Long.format(format: String): String = try {
    DecimalFormat(format).format(this)
} catch (_: Exception) {
    ""
}

actual fun Double.format(format: String): String = try {
    DecimalFormat(format).format(this)
} catch (_: Exception) {
    ""
}

actual fun Float.format(format: String): String = try {
    DecimalFormat(format).format(this)
} catch (_: Exception) {
    ""
}

actual fun LocalDate?.format(format: String): String {
    return this?.let { date ->
        val longSeconds = (date.toEpochDays() * 24 * 3600000).toLong()

        SimpleDateFormat(format).format(Date(longSeconds))
    } ?: ""
}

actual fun LocalDateTime?.format(format: String): String {
    return this?.let { date ->
        val longSeconds = (date.toJavaLocalDateTime().toEpochSecond(ZoneOffset.UTC) * 1000).toLong()

        SimpleDateFormat(format).format(Date(longSeconds))
    } ?: ""
}

actual fun String?.toLocalDate(format: String): LocalDate? = try {
    val date = SimpleDateFormat(format, Locale.getDefault()).parse(this!!)
    Calendar.getInstance().let { calendar ->
        calendar.time = date!!
        LocalDate(
            year = calendar.get(Calendar.YEAR),
            monthNumber = calendar.get(Calendar.MONTH) + 1,
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
} catch (_: Exception) {
    null
}

actual fun String?.toLocalDateTime(format: String): LocalDateTime? = try {
    val date = SimpleDateFormat(format, Locale.getDefault()).parse(this!!)
    Calendar.getInstance().let { calendar ->
        calendar.time = date!!
        LocalDateTime(
            year = calendar.get(Calendar.YEAR),
            monthNumber = calendar.get(Calendar.MONTH) + 1,
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH),
            hour = calendar.get(Calendar.HOUR),
            minute = calendar.get(Calendar.MINUTE),
            second = calendar.get(Calendar.SECOND)
        )
    }
} catch (_: Exception) {
    null
}
