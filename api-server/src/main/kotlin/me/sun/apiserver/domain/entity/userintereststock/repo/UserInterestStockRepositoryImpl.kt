package me.sun.apiserver.domain.entity.userintereststock.repo

import com.querydsl.jpa.impl.JPAQueryFactory
import me.sun.apiserver.domain.entity.userintereststock.QUserInterestStock.userInterestStock
import me.sun.apiserver.domain.entity.userintereststock.UserInterestStock

class UserInterestStockRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserInterestStockRepositoryCustom {
    override fun softDelete(userId: Long, stockId: Long): Long =
        jpaQueryFactory.update(userInterestStock)
            .set(userInterestStock.deleted, true)
            .where(
                userInterestStock.userId.eq(userId)
                    .and(userInterestStock.stockId.eq(stockId))
            )
            .execute()

    override fun findAllByUserIdAndStockIds(userId: Long, stockIds: List<Long>): List<UserInterestStock> =
        jpaQueryFactory.selectFrom(userInterestStock)
            .where(
                userInterestStock.userId.eq(userId)
                    .and(userInterestStock.stockId.`in`(stockIds))
                    .and(userInterestStock.deleted.isFalse)
            )
            .fetch()
}
