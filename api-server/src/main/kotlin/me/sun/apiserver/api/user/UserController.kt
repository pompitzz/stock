package me.sun.apiserver.api.user

import me.sun.apiserver.application.stock.StockContext
import me.sun.apiserver.application.stock.StockService
import me.sun.apiserver.application.user.UserInterestStockService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userInterestStockService: UserInterestStockService,
    private val stockService: StockService
) {
    @PostMapping("/interestStock")
    fun addInterestStock(@RequestParam userId: Long, @RequestParam stockId: Long) {
        userInterestStockService.add(userId, stockId)
    }

    @DeleteMapping("/interestStock")
    fun deleteInterestStock(@RequestParam userId: Long, @RequestParam stockId: Long) {
        userInterestStockService.delete(userId, stockId)
    }

    @GetMapping("/interestStock")
    fun findUserInterestStocks(@RequestParam userId: Long): FindUserInterestStockResponse {
        val stockIds: List<Long> = userInterestStockService.findInterestStockIds(userId)
        val stockContexts: List<StockContext> = stockService.findStockContexts(stockIds)
        return FindUserInterestStockResponse(stockContexts)
    }
}
