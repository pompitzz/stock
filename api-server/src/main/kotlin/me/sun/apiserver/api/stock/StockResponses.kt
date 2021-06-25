package me.sun.apiserver.api.stock

import me.sun.apiserver.api.MyPage
import me.sun.apiserver.application.stock.StockContext
import me.sun.apiserver.application.stock.StockSummary


class SingleStockContextResponse(
    val stockContext: StockContext
)

class StockSearchResponse(
    val stockSummaries: MyPage<StockSummary>
)
