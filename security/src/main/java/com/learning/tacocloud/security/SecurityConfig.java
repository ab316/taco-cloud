package com.learning.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private PasswordEncoder encoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/design", "/orders/**").hasRole("USER")
                .antMatchers("/design", "/orders/**", "/api/**").permitAll()
                .antMatchers("/", "/**").permitAll();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design", true);

        http.logout()
                .logoutSuccessUrl("/");

        // Needed for H2 console and (for now) the REST api
        http.csrf()
                .ignoringAntMatchers("/h2-console/**", "/api/**");

        // Needed for H2 console
        http.headers()
                .frameOptions()
                .sameOrigin();
    }
}
