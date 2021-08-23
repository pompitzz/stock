package me.sun.apiserver.application.stock

import me.sun.apiserver.domain.entity.stock.repo.StockRepository
import me.sun.apiserver.domain.entity.userintereststock.UserInterestStock
import me.sun.apiserver.domain.entity.userintereststock.repo.UserInterestStockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InterestStockService(
    private val userInterestStockRepository: UserInterestStockRepository,
    private val stockRepository: StockRepository,
) {
    fun add(userId: Long, stockId: Long) {
        userInterestStockRepository.save(UserInterestStock(userId = userId, stockId = stockId))
    }

    @Transactional
    fun delete(userId: Long, stockId: Long) {
        userInterestStockRepository.findByUserIdAndStockId(userId, stockId)
            ?: throw IllegalArgumentException("can not find entity. userId: $userId, stockId: $stockId")

        userInterestStockRepository.softDelete(userId, stockId)
    }

    fun findInterestStocks(userId: Long): List<StockDetail> {
        val stocks = stockRepository.findInterestStocksByUserId(userId)
        return stocks.map { StockDetail.from(it) }
    }

    fun findInterestStockIds(userId: Long?, stockIds: List<Long>): Set<Long> {
        if (stockIds.isEmpty() || userId == null) return emptySet()
        return userInterestStockRepository.findAllByUserIdAndStockIds(userId, stockIds)
            .map { it.stockId }
            .toSet()
    }
}
