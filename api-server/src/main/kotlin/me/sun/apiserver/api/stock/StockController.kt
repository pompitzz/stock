package me.sun.apiserver.api.stock

import me.sun.apiserver.application.StockContextService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stock")
class StockController(
    private val stockContextService: StockContextService
) {
    @GetMapping("/search")
    fun searchStock(@RequestParam query: String): StockSummariesResponse {
        return TODO()
    }

    @GetMapping("/context/{symbol}")
    fun findStockContext(@PathVariable symbol: String, @RequestParam period: Int): SingleStockContextResponse {
        if (period < 7) throw IllegalArgumentException("period should be greater than or equal 7 but is $period")
        val stockContext = stockContextService.getStockContext(symbol, period.toLong())
        return SingleStockContextResponse(stockContext)
    }
}
