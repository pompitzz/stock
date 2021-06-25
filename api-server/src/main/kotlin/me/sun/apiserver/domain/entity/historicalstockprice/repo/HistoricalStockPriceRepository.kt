package me.sun.apiserver.domain.entity.historicalstockprice.repo

import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import org.springframework.data.jpa.repository.JpaRepository

interface HistoricalStockPriceRepository :
    JpaRepository<HistoricalStockPrice, Long>,
    HistoricalStockPriceRepositoryCustom
