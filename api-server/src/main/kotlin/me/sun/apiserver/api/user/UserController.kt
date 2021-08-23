package me.sun.apiserver.api.user

import me.sun.apiserver.application.stock.InterestStockService
import me.sun.apiserver.application.stock.StockDetail
import me.sun.apiserver.application.stock.StockService
import me.sun.apiserver.common.UserContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val interestStockService: InterestStockService,
    private val stockService: StockService
) {
    @GetMapping("/profile")
    fun profile(): String {
        val user = UserContextHolder.getUser()
        return user?.userName ?: "not found user"
    }

    @PostMapping("/interest-stock")
    fun addInterestStock(@RequestParam userId: Long, @RequestParam stockId: Long) {
        interestStockService.add(userId, stockId)
    }

    @DeleteMapping("/interest-stock")
    fun deleteInterestStock(@RequestParam userId: Long, @RequestParam stockId: Long) {
        interestStockService.delete(userId, stockId)
    }

    @GetMapping("/interest-stock")
    fun findUserInterestStocks(): FindInterestStocksResponse {
        val userId = UserContextHolder.getUserId() ?: throw IllegalStateException("userId should not be null")
        val stockContexts: List<StockDetail> = interestStockService.findInterestStocks(userId)
        return FindInterestStocksResponse(stockContexts)
    }
}
