package me.sun.apiserver.domain.entity.stock.repo

import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long>, StockRepositoryCustom {
    fun findBySymbol(symbol: String): Stock?
}
