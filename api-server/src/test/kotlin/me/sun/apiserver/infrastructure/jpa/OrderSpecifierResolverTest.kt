package me.sun.apiserver.infrastructure.jpa

import com.querydsl.core.types.Order
import com.querydsl.core.types.Visitor
import com.querydsl.core.types.dsl.BeanPath
import com.querydsl.core.types.dsl.ComparableExpressionBase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

internal class OrderSpecifierResolverTest {
    @Test
    fun `resolve noException whenSortIsEmpty`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when
        val orderSpecifiers = OrderSpecifierResolver.resolve(pageable, emptyMap())

        // then
        Assertions.assertThat(orderSpecifiers).isEmpty()
    }

    @Test
    fun resolve() {
        // given
        val pageable: Pageable = PageRequest.of(
            0, 10, Sort.by(
                Sort.Order.asc("createdDate"),
                Sort.Order.asc("id"),  // should be filtered
                Sort.Order.desc("itemCount")
            )
        )
        val createdDateBeanPath = BeanPath(Int::class.java, "createdDate")
        val itemCountBeanPath = BeanPath(Int::class.java, "itemCount")

        val expressionByProperty: Map<String, ComparableExpressionBase<*>> = mapOf(
            "createdDate" to emptyExpression(createdDateBeanPath),
            "itemCount" to emptyExpression(itemCountBeanPath)
        )

        // when
        val orderSpecifiers = OrderSpecifierResolver.resolve(pageable, expressionByProperty)

        // then
        Assertions.assertThat(orderSpecifiers.size).isEqualTo(2)
        Assertions.assertThat(orderSpecifiers[0].order).isEqualTo(Order.ASC)
        Assertions.assertThat(orderSpecifiers[0].target).isEqualTo(createdDateBeanPath)
        Assertions.assertThat(orderSpecifiers[1].order).isEqualTo(Order.DESC)
        Assertions.assertThat(orderSpecifiers[1].target).isEqualTo(itemCountBeanPath)
    }

    private fun emptyExpression(beanPath: BeanPath<Int>): ComparableExpressionBase<Int> {
        return object : ComparableExpressionBase<Int>(beanPath) {
            override fun <R, C> accept(v: Visitor<R, C>, context: C?): R? {
                return null
            }
        }
    }
}
