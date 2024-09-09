package com.myserver.springserver.services;

import com.myserver.springserver.dto.JwtResponse;
import com.myserver.springserver.dto.SignUpRequest;
import com.myserver.springserver.dto.SignInRequest;
import com.myserver.springserver.exception.AlreadyExistException;

public interface AuthService {
    JwtResponse signUp(SignUpRequest request) throws AlreadyExistException;

    JwtResponse signIn(SignInRequest request);
}
