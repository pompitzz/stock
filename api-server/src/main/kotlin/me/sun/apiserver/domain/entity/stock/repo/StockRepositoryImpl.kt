package me.sun.apiserver.domain.entity.stock.repo

import com.querydsl.jpa.impl.JPAQueryFactory
import me.sun.apiserver.domain.entity.stock.QStock.stock
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.domain.entity.userintereststock.QUserInterestStock
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class StockRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : StockRepositoryCustom {
    override fun search(query: String, pageable: Pageable): Page<Stock> {
        val fetchResults = jpaQueryFactory.selectFrom(stock)
            .where(
                stock.symbol.containsIgnoreCase(query)
                    .or(stock.name.containsIgnoreCase(query))
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
        return PageImpl(fetchResults.results, pageable, fetchResults.total)
    }

    override fun findInterestStocksByUserId(userId: Long): List<Stock> =
        jpaQueryFactory.selectFrom(stock)
            .join(QUserInterestStock.userInterestStock).on(stock.id.eq(QUserInterestStock.userInterestStock.stockId))
            .where(
                QUserInterestStock.userInterestStock.userId.eq(userId)
                    .and(QUserInterestStock.userInterestStock.deleted.isFalse)
            )
            .fetch()
}
