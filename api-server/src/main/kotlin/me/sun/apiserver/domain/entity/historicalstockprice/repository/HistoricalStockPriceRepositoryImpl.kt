package me.sun.apiserver.domain.entity.historicalstockprice.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.historicalstockprice.QHistoricalStockPrice.historicalStockPrice
import java.time.LocalDate

class HistoricalStockPriceRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : HistoricalStockPriceRepositoryCustom {
    override fun findAllAfterOrEqualToTargetDate(stockId: Long, targetDate: LocalDate): List<HistoricalStockPrice> =
        queryFactory
            .selectFrom(historicalStockPrice)
            .where(
                historicalStockPrice.stockId.eq(stockId)
                    .and(
                        historicalStockPrice.date.after(targetDate)
                            .or(historicalStockPrice.date.eq(targetDate))
                    )
            )
            .fetch()
}
