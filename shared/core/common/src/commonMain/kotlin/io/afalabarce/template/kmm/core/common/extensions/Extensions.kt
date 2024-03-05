@file:OptIn(ExperimentalTime::class)

package io.afalabarce.template.kmm.core.common.extensions

import kotlinx.datetime.*
import kotlin.time.ExperimentalTime

fun <T> Boolean.iif(ifTrue: () -> T, ifFalse: () -> T): T = if (this) ifTrue() else ifFalse()

expect fun Int.format(format: String = "#,##0"): String

expect fun Long.format(format: String = "#,##0"): String

expect fun Double.format(format: String = "#,##0.00"): String
expect fun Float.format(format: String = "#,##0.00"): String

expect fun LocalDate?.format(format: String = "dd-MM-yyyy"): String
expect fun LocalDateTime?.format(format: String = "dd-MM-yyyy'T'HH:mm:ss"): String
expect fun String?.toLocalDate(format: String = "dd-MM-yyyy"): LocalDate?
expect fun String?.toLocalDateTime(format: String = "dd-MM-yyyy'T'HH:mm:ss"): LocalDateTime?

infix fun LocalDate.plusDays(days: Long): LocalDate = this.plus(days, DateTimeUnit.DAY)
infix fun LocalDate.plusMonth(month: Int): LocalDate = this.plus(month, DateTimeUnit.MONTH)
infix fun LocalDateTime.plusDays(days: Long): LocalDateTime = LocalDate(
    year = this.year,
    monthNumber = this.monthNumber,
    dayOfMonth = this.dayOfMonth
).plusDays(days).let { localDate ->
    LocalDateTime(
        year = localDate.year,
        monthNumber = localDate.monthNumber,
        dayOfMonth = localDate.dayOfMonth,
        hour = this.hour,
        minute = this.minute,
        second = this.second,
        nanosecond = nanosecond
    )
}

infix fun LocalDateTime.plusMonth(month: Int): LocalDateTime = LocalDate(
    year = this.year,
    monthNumber = this.monthNumber,
    dayOfMonth = this.dayOfMonth
).plusMonth(month).let { localDate ->

    LocalDateTime(
        year = localDate.year,
        monthNumber = localDate.monthNumber,
        dayOfMonth = localDate.dayOfMonth,
        hour = this.hour,
        minute = this.minute,
        second = this.second,
        nanosecond = nanosecond
    )
}

fun LocalDate.getMondayOfWeek(): LocalDate =
    this.plus(
        (DayOfWeek.MONDAY.ordinal - this.dayOfWeek.ordinal).toLong(), DateTimeUnit.DAY
    )


fun LocalDate.getFirstDayOfMonth(): LocalDate = LocalDate(this.year, this.month, 1)

fun <T> allIsNull(vararg values: T?): Boolean = values.all { x -> x == null }

fun <T> anyIsNotNull(vararg values: T?): Boolean = values.any { x -> x != null }

fun allStringsAreNullOrEmpty(vararg values: String?): Boolean = values.all { x -> x.isNullOrEmpty() }

fun anyStringIsNotNullOrEmpty(vararg values: String?): Boolean = values.any { x -> x != null }
