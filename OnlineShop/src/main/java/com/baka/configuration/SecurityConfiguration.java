package com.baka.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		    .csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/", "/user/registerUser", "/productDetails", "/searchByCategory", "/searchProduct","/resources/**", "/css/**", "/image/**").permitAll()
		    .antMatchers("/myAcount/**").hasAnyAuthority("USER", "ADMIN")
		    .antMatchers("/shoppingCart/**", "/checkOut/**").hasAuthority("USER")
		    .antMatchers("/product/**").hasAuthority("ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/login").permitAll().defaultSuccessUrl("/myAccount/viewAccount")
		    .and()
		    .logout().invalidateHttpSession(true)
		    .clearAuthentication(true)
		    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .logoutSuccessUrl("/login").permitAll();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		    .userDetailsService(userDetailsService)
		    .passwordEncoder(passwordEncoder());
	}
	
	
	
	
}
