package me.sun.apiserver.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.DayOfWeek

// private val log = logger<ClassName>()
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun DayOfWeek.isWeekend() = this == DayOfWeek.SATURDAY || this == DayOfWeek.SUNDAY
