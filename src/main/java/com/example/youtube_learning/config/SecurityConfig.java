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
public class SecurityConfig {

	@Bean
//	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}

//	InMemoryUserDetailsManager is child of userdetailsservice which is non-persistent
//	public User(String username, String password, boolean enabled, boolean accountNonExpired,
//	boolean credentialsNonExpired, boolean accountNonLocked,
//	Collection<? extends GrantedAuthority> authorities)
	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails users=new User("user1", "User@01", true, true, true, true, null);
		UserDetails user1 = User.withUsername("user1").password("{noop}password").roles("USER").build();
		UserDetails user2 = User.withUsername("user2").password("{noop}password").roles("ADMIN").build();
//		System.out.println(users.getPassword());
		return new InMemoryUserDetailsManager(user1,user2);
	}

}
