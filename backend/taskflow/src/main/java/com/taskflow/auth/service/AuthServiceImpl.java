package com.taskflow.auth.service;

import org.springframework.stereotype.Service;

import com.taskflow.auth.dto.AuthResponse;
import com.taskflow.auth.dto.LoginRequest;
import com.taskflow.auth.dto.RegisterRequest;
import com.taskflow.auth.dto.UserResponse;
import com.taskflow.user.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public AuthResponse register(RegisterRequest registerRequest) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'register'");
	}

	@Override
	public AuthResponse login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'login'");
	}

	@Override
	public UserResponse getCurrentUser(String email) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCurrentUser'");
	}
}
