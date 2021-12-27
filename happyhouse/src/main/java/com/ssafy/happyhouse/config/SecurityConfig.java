package com.ssafy.happyhouse.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
    	// disable default Spring Security Login Page
    	security.csrf().disable()
    			.httpBasic().disable()
    			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    			.and()
    			.authorizeRequests().and()
		        .authorizeRequests().anyRequest().anonymous().and().cors();
    }
}
