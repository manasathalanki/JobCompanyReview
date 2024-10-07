package com.example.youtube_learning.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.youtube_learning.dto.LoginRequest;
import com.example.youtube_learning.dto.LoginResponse;
import com.example.youtube_learning.entity.Users;
import com.example.youtube_learning.jwt.JwtUtils;
import com.example.youtube_learning.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public LoginResponse authenticate(LoginRequest loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			Users user = userRepository.findByUsername(loginRequest.getUsername())
					.orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getUsername()));

			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

			String jwtToken = jwtUtils.generateToken(userDetails);

			List<String> roles = user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList());

			return new LoginResponse(jwtToken, user.getUsername(), roles);

		} catch (AuthenticationException e) {
			throw new org.springframework.security.authentication.BadCredentialsException(
					"Invalid username or password");
		}
	}

	@Override
	public Users signup(Users user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new IllegalArgumentException("User already exists with username: " + user.getUsername());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
