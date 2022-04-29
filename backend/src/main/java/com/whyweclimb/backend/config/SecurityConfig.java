package com.whyweclimb.backend.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.whyweclimb.backend.domain.user.service.JwtTokenProvider;
import com.whyweclimb.backend.intercepter.JwtAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
	public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
    
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity.csrf().disable();
    	httpSecurity.authorizeRequests()
    					.antMatchers("/user/information").access("hasRole('ROLE_USER')")
	                    .antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
	                            "/images/**", "/js/**", "/console/**", "/favicon.ico/**"
	                            ,"/v3/api-docs"
	                            ,"/swagger*/**"
	                            ,"/funding/**"
	                            ,"/user/id"
	                            ,"/user/login"
	                            ,"/user"
	                            ,"/chat/message"
	                            ,"/chat/**")
	                    .permitAll()
	                    .anyRequest().authenticated()
                    .and() 
	                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) 
	                    .authorizeRequests();

    }
}