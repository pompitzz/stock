package me.sun.apiserver.domain.entity

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
class StockPrice(
        @Id
        @Column(name = "stock_price_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(nullable = false)
        val date: LocalDate,
        @Column(nullable = false)
        val price: BigDecimal,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        val currency: Currency,
        @Column(nullable = false)
        val timeZone: String,
)

enum class Currency {
    USD, KRW
}
