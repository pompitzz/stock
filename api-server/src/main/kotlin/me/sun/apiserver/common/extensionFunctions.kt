package me.sun.apiserver.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

// private val log = logger<ClassName>()
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)
