package com.smart.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}


	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	 http
    .csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/user/**").hasRole("USER")
					.requestMatchers("/admin/**").hasRole("ADMIN") 
					.requestMatchers("/**").permitAll().anyRequest().authenticated()
			).formLogin(formLogin -> formLogin
					.loginPage("/signin") // Set your custom login page URL if needed
					.defaultSuccessUrl("/") // Set the default success URL after login
					.permitAll());
	 
		return http.build();
	}
	
	
	
//	@SuppressWarnings("removal")
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		
//		http.csrf().disable().authorizeHttpRequests().requestMatchers("/me").permitAll().and().formLogin();
//		
//		return http.build();
//	}
	


}
