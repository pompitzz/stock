package me.sun.apiserver.domain.entity.stock.repo

import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface StockRepositoryCustom {
    fun search(query: String, pageable: Pageable): Page<Stock>

    fun findInterestStocksByUserId(userId: Long): List<Stock>
}
