package io.afalabarce.template.kmm.core.common.extensions

import kotlinx.datetime.*
import platform.Foundation.*

actual fun Int.format(format: String): String = try {
    val formatter = NSNumberFormatter()
    formatter.positiveFormat = format
    formatter.negativeFormat = "-$format"

    formatter.stringFromNumber(NSNumber.numberWithInt(this)) ?: ""
} catch (_: Exception) {
    ""
}

actual fun Long.format(format: String): String = try {
    val formatter = NSNumberFormatter()
    formatter.positiveFormat = format
    formatter.negativeFormat = "-$format"

    formatter.stringFromNumber(NSNumber.numberWithLong(this)) ?: ""
} catch (_: Exception) {
    ""
}

actual fun Double.format(format: String): String = try {
    val formatter = NSNumberFormatter()
    formatter.positiveFormat = format
    formatter.negativeFormat = "-$format"

    formatter.stringFromNumber(NSNumber.numberWithDouble(this)) ?: ""
} catch (_: Exception) {
    ""
}

actual fun Float.format(format: String): String = try {
    val formatter = NSNumberFormatter()
    formatter.positiveFormat = format
    formatter.negativeFormat = "-$format"

    formatter.stringFromNumber(NSNumber.numberWithFloat(this)) ?: ""
} catch (_: Exception) {
    ""
}

actual fun LocalDate?.format(format: String): String = try {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    dateFormatter.stringFromDate(this!!.toInstant().toNSDate())
} catch (e: Exception) {
    ""
}

actual fun LocalDateTime?.format(format: String): String = try {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    dateFormatter.stringFromDate(this!!.toInstant(UtcOffset.ZERO).toNSDate())
} catch (e: Exception) {
    ""
}

actual fun String?.toLocalDate(format: String): LocalDate? = try {
    NSDateFormatter().let { formatter ->
        formatter.dateFormat = format
        val result: Long = formatter.dateFromString(string = this!!)?.timeIntervalSince1970?.toLong() ?: 0L

        LocalDate.fromEpochDays((result / (24 * 3600)).toInt())
    }
} catch (_: Exception) {
    null
}

actual fun String?.toLocalDateTime(format: String): LocalDateTime? = try {
    NSDateFormatter().let { formatter ->
        formatter.dateFormat = format
        val result = formatter.dateFromString(string = this!!)!!.toKotlinInstant()

        result.toLocalDateTime(TimeZone.UTC)
    }
} catch (_: Exception) {
    null
}

private fun LocalDate.toInstant(): Instant = Instant.fromEpochSeconds(this.toEpochDays() * 24 * 3600L)
