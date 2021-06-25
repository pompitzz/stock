package me.sun.apiserver.domain.entity.userintereststock

import javax.persistence.*

@Entity
@Table(
    name = "user_interest_stock",
    uniqueConstraints = [
        UniqueConstraint(name = "id_unique_constraints", columnNames = ["user_id", "stock_id"])
    ]
)
class UserInterestStock(
    @Id
    @Column(name = "user_interest_stock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "stock_id", nullable = false)
    val stockId: Long,
    val deleted: Boolean = false
)
