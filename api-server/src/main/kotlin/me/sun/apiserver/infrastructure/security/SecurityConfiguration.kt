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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfiguration(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
    private val jwtAuthenticationConverter: JwtAuthenticationConverter,
    private val noOpAuthenticationSuccessHandler: AuthenticationSuccessHandler,
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
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .anyRequest()
            .permitAll()

        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    fun authenticationFilter(): AuthenticationFilter {
        val filter = AuthenticationFilter(authenticationManagerBean(), jwtAuthenticationConverter)
        filter.successHandler = noOpAuthenticationSuccessHandler
        filter.requestMatcher = AuthRequestMatcher()
        return filter
    }
}
