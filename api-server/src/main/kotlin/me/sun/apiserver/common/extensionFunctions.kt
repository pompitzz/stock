package me.sun.apiserver.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.*
import java.time.temporal.WeekFields

// private val log = logger<ClassName>()
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun DayOfWeek.isWeekend() = this == DayOfWeek.SATURDAY || this == DayOfWeek.SUNDAY

fun LocalDateTime.toZonedDateTime(): ZonedDateTime = ZonedDateTime.of(this, ZoneId.systemDefault())
fun LocalDate.weekOfYear() = this.get(WeekFields.ISO.weekOfWeekBasedYear())
fun ZonedDateTime.weekOfYear() = this.toLocalDate().weekOfYear()
