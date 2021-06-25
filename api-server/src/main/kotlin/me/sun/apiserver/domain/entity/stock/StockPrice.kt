package me.sun.apiserver.domain.entity

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class StockPrice(
    @Column(nullable = false, name = "price_date")
    val date: LocalDate,
    @Column(nullable = false)
    val price: BigDecimal,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val currency: Currency,
)

enum class Currency {
    USD, KRW
}
