package com.nisum.ms.usermanagement.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nisum.ms.usermanagement.jwt.JwtAuthenticationEntryPoint;
import com.nisum.ms.usermanagement.jwt.JwtTokenFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtTokenFilter jwtRequestFilter;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("WebSecurityConfig.configureGlobal");
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        log.info("WebSecurityConfig.authenticationManagerBean");
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("WebSecurityConfig.configure");
        
		httpSecurity.authorizeRequests()
			        .antMatchers("/").permitAll()
			        .and()
			        .authorizeRequests()
				    .antMatchers("/h2-console/**").permitAll()
				    .and()
				    .headers().frameOptions().disable()
				    .and().csrf()
				    .ignoringAntMatchers("/h2-console/**").and().cors().disable();
        
		httpSecurity.authorizeRequests()
			        .antMatchers("/").permitAll()
			        .and()
			        .authorizeRequests()
				    .antMatchers("/v2/api-docs/**").permitAll()
				    .and()
				    .headers().frameOptions().disable()
				    .and().csrf()
				    .ignoringAntMatchers("/v2/api-docs/**").and().cors().disable();
		
		
		httpSecurity.authorizeRequests()
			        .antMatchers("/").permitAll()
			        .and()
			        .authorizeRequests()
				    .antMatchers("/swagger-ui.html/**").permitAll()
				    .and()
				    .headers().frameOptions().disable()
				    .and().csrf()
				    .ignoringAntMatchers("/swagger-ui.html/**").and().cors().disable();
		
        httpSecurity.csrf()
			        .disable()
			        .authorizeRequests()
			        .antMatchers("/api/token")
			        .permitAll()
			        .anyRequest()
			        .authenticated()
			        .and()
			        .exceptionHandling()
			        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
			        .and()
			        .sessionManagement()
			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Agregando un filtro para validar los tokens con cada solicitud
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
}