package me.sun.apiserver.domain.entity.stock

import me.sun.apiserver.domain.entity.StockPrice
import javax.persistence.*

@Entity
class Stock(
        @Id
        @Column(name = "stock_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @JoinColumn(name = "stock_price_id")
        @OneToOne
        val stockPrice: StockPrice,
        @Column(nullable = false, unique = true)
        val symbol: String,
        @Column(nullable = false)
        val exchange: String,
        @Column(nullable = false)
        val name: String,
        @Column(nullable = false)
        val market: String,
        val sector: String?,
        val state: String?,
        val industry: String?,
        val website: String?,
        val logoUrl: String?,
)
