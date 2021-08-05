package me.sun.apiserver.application.auth

interface TokenProvider {
    fun createToken(userId: String): String
}
