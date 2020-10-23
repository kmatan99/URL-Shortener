package com.example.shortening.controller;

import com.example.shortening.model.JwtRequest;
import com.example.shortening.model.JwtResponse;
import com.example.shortening.model.UserEntity;
import com.example.shortening.repository.UserRepository;
import com.example.shortening.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        UserEntity userEntity = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() ->
                new EntityNotFoundException("User by username:" + authenticationRequest.getUsername() + "doesn't exist"));

        User user = new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());

        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
