package me.sun.apiserver.domain.entity.stockprice

import me.sun.apiserver.domain.entity.StockPrice
import org.springframework.data.jpa.repository.JpaRepository

interface StockPriceRepository : JpaRepository<StockPrice, Long>
