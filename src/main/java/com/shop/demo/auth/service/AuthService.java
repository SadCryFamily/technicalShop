package com.shop.demo.auth.service;

import com.shop.demo.auth.pojo.LoginRequest;
import com.shop.demo.auth.pojo.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signupRequest);

}
