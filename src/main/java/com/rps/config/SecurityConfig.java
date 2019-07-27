package com.rps.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rps.service.tabulation.TeacherDetailsServiceImp;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	@Autowired
	private TeacherDetailsServiceImp teacherDetailsServiceImp;
	
	private static final String SALT = "salt";
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	
	 private static final String[] PUBLIC_MATCHERS = {
	            "/webjars/**",
	            "/css/**",
	            "/js/**",
	            "/images/**",
	            "/",
	            "/about/**",
	            "/contact/**",
	            "/error/**/*",
	            "/console/**",
	            "/tabulation/signup"
	    };
	 
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(teacherDetailsServiceImp).passwordEncoder(passwordEncoder());
		}

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	       
	    	http
	                .authorizeRequests()
//	                antMatchers("/**").
	                .antMatchers(PUBLIC_MATCHERS).permitAll()
	                .anyRequest().authenticated();

	        http
	                .csrf().disable().cors().disable()
	                .formLogin().loginPage("/tabulation/login").defaultSuccessUrl("/tabulation/showExams").failureUrl("/tabulation/login?error").permitAll()
	                .and()
	                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/tabulation/logout")).logoutSuccessUrl("/tabulation/login").deleteCookies("remember-me").permitAll()
	                .and()
	                .rememberMe();
	    }
}
