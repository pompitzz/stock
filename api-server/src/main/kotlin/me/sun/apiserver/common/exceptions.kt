package me.sun.apiserver

class StockException(message: String) : RuntimeException(message)
class ApiFailureException(message: String) : RuntimeException(message)
