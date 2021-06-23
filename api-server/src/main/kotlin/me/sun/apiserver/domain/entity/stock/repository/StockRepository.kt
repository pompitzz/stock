package me.sun.apiserver.domain.entity.stock.repository

import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long>
