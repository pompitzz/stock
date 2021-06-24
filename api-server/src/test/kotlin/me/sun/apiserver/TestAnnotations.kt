package me.sun.apiserver

import me.sun.apiserver.infrastructure.jpa.JpaConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaConfiguration::class)
annotation class MyDataJpaTest
