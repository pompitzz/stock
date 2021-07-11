package me.sun.apiserver.api.stock

import me.sun.apiserver.api.MyPage
import me.sun.apiserver.application.stock.StockContext


class StockContextResponse(
    val stockContext: StockContext
)

class StockSearchResponse(
    val stockContexts: MyPage<StockContext>
)
