package com.myserver.springserver.services.implementation;

import com.myserver.springserver.dto.JwtResponse;
import com.myserver.springserver.dto.SignInRequest;
import com.myserver.springserver.dto.SignUpRequest;
import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.model.MyUser;
import com.myserver.springserver.model.Role;
import com.myserver.springserver.security.JwtCore;
import com.myserver.springserver.services.AuthService;
import com.myserver.springserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtCore jwtCore;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public JwtResponse signUp(SignUpRequest request) throws AlreadyExistException {
        Role role = (request.getIsAdmin() != null && request.getIsAdmin()) ? Role.ROLE_ADMIN : Role.ROLE_USER;

        MyUser user = MyUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();


        userService.add(user);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String jwt = jwtCore.generateToken(userDetails);
        return new JwtResponse(jwt);
    }

    @Override
    public JwtResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userDetailsService.loadUserByUsername(request.username);
        String jwt = jwtCore.generateToken(user);
        return new JwtResponse(jwt);
    }
}
