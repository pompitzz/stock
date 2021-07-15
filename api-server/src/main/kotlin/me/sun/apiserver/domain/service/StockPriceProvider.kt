package me.sun.apiserver.domain.service

import me.sun.apiserver.domain.entity.StockPrice

interface StockPriceProvider {
    fun getStockPrice(symbol: String): StockPrice
}
