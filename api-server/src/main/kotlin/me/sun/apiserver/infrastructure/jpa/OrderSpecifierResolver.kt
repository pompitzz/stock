package me.sun.apiserver.infrastructure.jpa

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.ComparableExpressionBase
import me.sun.apiserver.common.logger
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort


object OrderSpecifierResolver {

    private val log = logger<OrderSpecifierResolver>()

    fun resolve(
        pageable: Pageable,
        expressionByProperty: Map<String, ComparableExpressionBase<*>>
    ): Array<OrderSpecifier<*>> =
        pageable.sort
            .map { doResolve(it, expressionByProperty) }
            .filterNotNull()
            .toTypedArray()

    private fun doResolve(
        order: Sort.Order,
        expressionByProperty: Map<String, ComparableExpressionBase<*>>
    ): OrderSpecifier<*>? {
        val comparableExpressionBase: ComparableExpressionBase<*>? = expressionByProperty[order.property]
        if (comparableExpressionBase == null) {
            log.warn("invalid order property. property: {}", order.property)
            return null
        }
        if (order.isAscending) {
            return comparableExpressionBase.asc()
        }
        return comparableExpressionBase.desc()
    }
}
