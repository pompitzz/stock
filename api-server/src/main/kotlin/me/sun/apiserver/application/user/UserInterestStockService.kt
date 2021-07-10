package me.sun.apiserver.application.user

import me.sun.apiserver.domain.entity.userintereststock.UserInterestStock
import me.sun.apiserver.domain.entity.userintereststock.repo.UserInterestStockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInterestStockService(
    private val userInterestStockRepository: UserInterestStockRepository
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

    fun findInterestStockIds(userId: Long): List<Long> {
        return userInterestStockRepository.findAllByUserId(userId).map { it.stockId }
    }

    fun findExistsStockIds(userId: Long?, stockIds: List<Long>): Set<Long> {
        if (stockIds.isEmpty() || userId == null) return emptySet()
        return userInterestStockRepository.findAllByUserIdAndStockIds(userId, stockIds)
            .map { it.stockId }
            .toSet()
    }
}
