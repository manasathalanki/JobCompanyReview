package com.example.youtube_learning.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

	@Bean
//	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.csrf(csrf -> csrf.disable());
		return http.build();
	}

//	InMemoryUserDetailsManager is child of userdetailsservice which is non-persistent
//	public User(String username, String password, boolean enabled, boolean accountNonExpired,
//	boolean credentialsNonExpired, boolean accountNonLocked,
//	Collection<? extends GrantedAuthority> authorities)

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user1 = User.withUsername("user1").password("{noop}password").roles("USER").build();
		UserDetails user2 = User.withUsername("user2").password("{noop}password").roles("ADMIN").build();
		return new InMemoryUserDetailsManager(user1, user2);
	}

}
