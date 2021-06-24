package me.sun.apiserver.domain.entity.historicalstockprice

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
class HistoricalStockPrice(
    @Id
    @Column(name = "historical_stock_price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val stockId: Long,
    @Column(nullable = false)
    val date: LocalDate,
    @Column(nullable = false)
    val open: BigDecimal,
    @Column(nullable = false)
    val high: BigDecimal,
    @Column(nullable = false)
    val low: BigDecimal,
    @Column(nullable = false)
    val close: BigDecimal,
    @Column(nullable = false)
    val volume: BigDecimal,
)
