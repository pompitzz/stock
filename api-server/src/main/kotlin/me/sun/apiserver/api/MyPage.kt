package me.sun.apiserver.api

import org.springframework.data.domain.Page
import kotlin.streams.toList

class MyPage<T>(
    val content: List<T>,
    val lastPage: Boolean,
    val size: Int,
    val currentPage: Int,
    val totalPages: Int,
    val sort: List<String>
) {
    companion object {
        fun <T> from(page: Page<T>) = from(page) { it }

        fun <T, R> from(
            page: Page<T>,
            mapper: (content: List<T>) -> List<R>
        ): MyPage<R> = with(page) {
            MyPage(
                content = mapper(content),
                lastPage = isLast,
                size = size,
                currentPage = number,
                totalPages = totalPages,
                sort = sort.stream().map { "${it.property},${it.direction}" }.toList(),
            )
        }
    }
}
