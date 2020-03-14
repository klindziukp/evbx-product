package com.evbx.product.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *  Mock Security Configuration
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityMockConfig extends WebSecurityConfigurerAdapter {

    private static final String PATH_REGEX = "/v1/evbx/**";
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles(USER)
                .and()
                .withUser("admin").password("{noop}admin").roles(USER, ADMIN);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, PATH_REGEX).hasAnyRole(USER, ADMIN)
                .antMatchers(HttpMethod.POST, PATH_REGEX).hasRole(ADMIN)
                .antMatchers(HttpMethod.PATCH, PATH_REGEX).hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, PATH_REGEX).hasRole(ADMIN)
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
