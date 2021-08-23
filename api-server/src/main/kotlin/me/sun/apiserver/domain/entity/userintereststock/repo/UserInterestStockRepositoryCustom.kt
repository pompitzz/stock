package me.sun.apiserver.domain.entity.userintereststock.repo

import me.sun.apiserver.domain.entity.userintereststock.UserInterestStock

interface UserInterestStockRepositoryCustom {
    fun softDelete(userId: Long, stockId: Long): Long

    fun findAllByUserIdAndStockIds(userId: Long, stockIds: List<Long>): List<UserInterestStock>
}
