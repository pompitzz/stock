package me.sun.apiserver.api.stock

import me.sun.apiserver.application.StockContext
import me.sun.apiserver.application.StockSummary


class SingleStockContextResponse(
    val stockContext: StockContext
)

class StockSummariesResponse(
    val stockSummaries: List<StockSummary>
)
