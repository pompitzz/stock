package me.sun.apiserver.application.stock

import me.sun.apiserver.StockException
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.domain.entity.stock.repo.StockRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StockQueryService(
    private val stockRepository: StockRepository,
    private val stockSynchronizer: StockSynchronizer,
) {
    fun search(query: String, pageable: Pageable): Page<Stock> {
        val stockPage = stockRepository.search(query, pageable)
        sync(stockPage.content)
        return stockPage
    }

    fun findAllById(stockIds: Iterable<Long>): List<Stock> {
        val stocks = stockRepository.findAllById(stockIds)
        sync(stocks)
        return stocks
    }

    fun findBySymbol(symbol: String): Stock {
        val stock = stockRepository.findBySymbol(symbol) ?: throw StockException("cannot find stock. symbol: $symbol")
        sync(listOf(stock))
        return stock
    }

    private fun sync(stocks: List<Stock>) {
        stocks.forEach { stockSynchronizer.sync(it) }
    }
}
