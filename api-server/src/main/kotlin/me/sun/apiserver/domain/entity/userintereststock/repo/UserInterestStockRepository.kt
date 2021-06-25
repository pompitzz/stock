package me.sun.apiserver.domain.entity.userintereststock.repo

import me.sun.apiserver.domain.entity.userintereststock.UserInterestStock
import org.springframework.data.jpa.repository.JpaRepository

interface UserInterestStockRepository : JpaRepository<UserInterestStock, Long>, UserInterestStockRepositoryCustom {
    fun findByUserIdAndStockId(userId: Long, stockId: Long): UserInterestStock?
}
