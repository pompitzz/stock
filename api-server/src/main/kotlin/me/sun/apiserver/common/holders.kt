package me.sun.apiserver.common

val USER_ID_HOLDER = ThreadLocal.withInitial { 1L }
