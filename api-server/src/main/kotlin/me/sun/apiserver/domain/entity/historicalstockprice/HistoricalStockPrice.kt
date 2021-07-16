package me.sun.apiserver.domain.entity.historicalstockprice

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Table(
    name = "historical_stock_price",
    uniqueConstraints = [
        UniqueConstraint(name = "unique_constraints", columnNames = ["stock_id", "date"])
    ]
)
@Entity
class HistoricalStockPrice(
    @Id
    @Column(name = "historical_stock_price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, name = "stock_id")
    val stockId: Long,
    @Column(nullable = false, name = "date")
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
