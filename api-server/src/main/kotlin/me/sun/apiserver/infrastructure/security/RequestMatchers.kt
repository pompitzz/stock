package me.sun.apiserver.infrastructure.security

import org.springframework.security.web.util.matcher.AntPathRequestMatcher

val ADMIN_ROLE_REQUEST_MATCHER = AntPathRequestMatcher("/admin/**")
val USER_ROLE_REQUEST_MATCHER = AntPathRequestMatcher("/user/**")
val AUTHENTICATED_REQUEST_MATCHER = AntPathRequestMatcher("/token/**")
