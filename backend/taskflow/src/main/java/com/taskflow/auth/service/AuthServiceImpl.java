package com.taskflow.auth.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskflow.auth.dto.AuthResponse;
import com.taskflow.auth.dto.LoginRequest;
import com.taskflow.auth.dto.RegisterRequest;
import com.taskflow.auth.dto.UserResponse;
import com.taskflow.user.entity.User;
import com.taskflow.user.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	private final ModelMapper modelMapper;
    private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.modelMapper = new ModelMapper();
		this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public AuthResponse register(RegisterRequest registerRequest) {
		User user = modelMapper.map(registerRequest, User.class);
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userRepository.save(user);
		String token = jwtService.generarToken(user);
		Timestamp expirationTime = jwtService.getExpirationTime(token);
		return new AuthResponse(token, token, expirationTime);
	}

	@Override
	public AuthResponse login(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
		String token = jwtService.generarToken(user);
		Timestamp expirationTime = jwtService.getExpirationTime(token);
		return new AuthResponse(token, token, expirationTime);
	}

	@Override
	public UserResponse getCurrentUser(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return modelMapper.map(user, UserResponse.class);
	}
}
