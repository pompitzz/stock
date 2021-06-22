package me.sun.apiserver.domain.entity

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
class HistoricalStockPrice(
        @Id
        @Column(name = "historical_stock_price_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
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
