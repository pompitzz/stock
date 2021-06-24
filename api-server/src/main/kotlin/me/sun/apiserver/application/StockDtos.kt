package me.sun.apiserver.application

import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.stock.Stock
import java.math.BigDecimal
import java.time.LocalDate

class StockContext(
    val stockSummary: StockSummary,
    val historicalPrices: List<SimpleHistoricalPrice>
)

class StockSummary(
    val stockId: Long,
    val symbol: String,
    val name: String,
    val price: BigDecimal,
    val priceDate: LocalDate,
    val currency: Currency,
) {
    companion object {
        fun fromEntity(stock: Stock) = with(stock) {
            StockSummary(
                stockId = id!!,
                symbol = symbol,
                name = name,
                price = stockPrice.price,
                priceDate = stockPrice.date,
                currency = stockPrice.currency,
            )
        }
    }
}

class SimpleHistoricalPrice(
    val price: BigDecimal,
    val date: LocalDate
)
