package com.mvp.match.vendingMachine.security


import com.mvp.match.vendingMachine.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Configuration
@Component
@EnableWebSecurity
class UserSecurityConfig : WebSecurityConfigurerAdapter() {


    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun createUserDetailsService(): UserDetailsService? {
        return UserDetailsServiceImpl()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        /*http.authorizeRequests()
            .antMatchers("/", "/registration").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").defaultSuccessUrl("/welcome").failureUrl("/welcome").permitAll()
            .and()
            .sessionManagement()
            .maximumSessions(1);*/

    }


    private val AUTH_WHITELIST = arrayOf(
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**"
    )

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(*AUTH_WHITELIST)
    }

}


