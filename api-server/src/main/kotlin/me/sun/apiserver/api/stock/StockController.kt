package me.sun.apiserver.api.stock

import me.sun.apiserver.application.stock.StockSearchCondition
import me.sun.apiserver.application.stock.StockSearchPeriodType
import me.sun.apiserver.application.stock.StockService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stock")
class StockController(
    private val stockService: StockService
) {
    @GetMapping("/search")
    fun searchStock(@RequestParam query: String,
                    @RequestParam withDetails: Boolean = true,
                    pageable: Pageable): StockSearchResponse {
        val searchCondition = StockSearchCondition(query, withDetails, StockSearchPeriodType.M_1)
        val stockSummaryPage = stockService.search(searchCondition, pageable)
        return StockSearchResponse(stockSummaryPage)
    }

    @GetMapping("/context/{symbol}")
    fun findStockContext(@PathVariable symbol: String,
                         @RequestParam period: StockSearchPeriodType): StockContextResponse {
        val stockContext = stockService.findStockContext(symbol, period)
        return StockContextResponse(stockContext)
    }
}
