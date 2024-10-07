package com.example.youtube_learning.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.youtube_learning.dto.LoginRequest;
import com.example.youtube_learning.dto.LoginResponse;
import com.example.youtube_learning.entity.Users;
import com.example.youtube_learning.service.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@Tag(name = "Token Generator")
public class LoginController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		try {
			LoginResponse loginResponse = authenticationService.authenticate(loginRequest);
			return ResponseEntity.ok(loginResponse);

		} catch (AuthenticationException exception) {
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Bad credentials");
			response.put("status", false);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Users user) {
		Users registeredUser = authenticationService.signup(user);

		return ResponseEntity.ok(registeredUser);
	}
}
