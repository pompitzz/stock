package me.sun.apiserver.infrastructure.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.OrRequestMatcher

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfiguration(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
    private val jwtAuthenticationConverter: JwtAuthenticationConverter,
    private val jwtAuthenticationFailureHandler: JwtAuthenticationFailureHandler,
    private val noOpAuthenticationSuccessHandler: NoOpAuthenticationSuccessHandler,
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(jwtAuthenticationProvider)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .requestMatchers(ADMIN_ROLE_REQUEST_MATCHER).hasRole("ADMIN")
            .requestMatchers(USER_ROLE_REQUEST_MATCHER).hasRole("USER")
            .requestMatchers(AUTHENTICATED_REQUEST_MATCHER).authenticated()
            .anyRequest()
            .permitAll()

        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    fun authenticationFilter(): AuthenticationFilter {
        val filter = AuthenticationFilter(authenticationManagerBean(), jwtAuthenticationConverter)
        filter.successHandler = noOpAuthenticationSuccessHandler
        filter.failureHandler = jwtAuthenticationFailureHandler
        filter.requestMatcher = OrRequestMatcher(ADMIN_ROLE_REQUEST_MATCHER, USER_ROLE_REQUEST_MATCHER, AUTHENTICATED_REQUEST_MATCHER)
        return filter
    }
}
