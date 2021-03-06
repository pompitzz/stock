package me.sun.apiserver.application.stock

import me.sun.apiserver.application.stock.StockSearchSelectionTimeUnit.DAY
import me.sun.apiserver.application.stock.StockSearchSelectionTimeUnit.WEEK
import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.stock.Stock
import java.math.BigDecimal
import java.time.LocalDate

data class StockContext(
    val stockDetail: StockDetail,
    val interest: Boolean = false,
    val historicalPrices: List<HistoricalStockPriceDto> = emptyList(),
)

data class StockDetail(
    val stockId: Long,
    val symbol: String,
    val name: String,
    val price: BigDecimal,
    val priceDate: LocalDate,
    val currency: Currency,
) {
    companion object {
        fun from(stock: Stock) = with(stock) {
            StockDetail(
                stockId = id,
                symbol = symbol,
                name = name,
                price = stockPrice.price,
                priceDate = stockPrice.date,
                currency = stockPrice.currency,
            )
        }
    }
}

data class StockSearchCondition(
    val query:String,
    val withDetails: Boolean,
    val periodType: StockSearchPeriodType,
)

data class HistoricalStockPriceDto(
    val date: LocalDate,
    val open: BigDecimal,
    val high: BigDecimal,
    val low: BigDecimal,
    val close: BigDecimal,
    val volume: BigDecimal,
) {
    companion object {
        fun from(historicalPrice: HistoricalStockPrice) = HistoricalStockPriceDto(
            date = historicalPrice.date,
            open = historicalPrice.open,
            high = historicalPrice.high,
            low = historicalPrice.low,
            close = historicalPrice.close,
            volume = historicalPrice.volume,
        )
    }
}

enum class StockSearchPeriodType(
    val desc: String,
    val selectionTimeUnit: StockSearchSelectionTimeUnit,
) {
    W_1("1 week", DAY),
    W_2("2 weeks", DAY),
    W_3("3 weeks", DAY),
    M_1("1 month", DAY),
    M_3("3 months", DAY),
    M_6("6 months", DAY),
    Y_1("1 year", DAY),
    Y_3("3 years", WEEK),
    Y_5("5 years", WEEK),
    Y_10("10 years", WEEK);


    fun getStartDate(): LocalDate = when (this) {
        W_1 -> LocalDate.now().minusWeeks(1)
        W_2 -> LocalDate.now().minusWeeks(2)
        W_3 -> LocalDate.now().minusWeeks(3)
        M_1 -> LocalDate.now().minusMonths(1)
        M_3 -> LocalDate.now().minusMonths(3)
        M_6 -> LocalDate.now().minusMonths(6)
        Y_1 -> LocalDate.now().minusYears(1)
        Y_3 -> LocalDate.now().minusYears(3)
        Y_5 -> LocalDate.now().minusYears(5)
        Y_10 -> LocalDate.now().minusYears(10)
    }
}

fun main() {
    for (stockSearchDivideType in StockSearchPeriodType.values().iterator()) {
        println(stockSearchDivideType.getStartDate())
    }
}

enum class StockSearchSelectionTimeUnit {
    DAY, WEEK, MONTH
}
