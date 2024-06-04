package com.findme.FindMeBack.Service;

import com.findme.FindMeBack.Entity.User;
import com.findme.FindMeBack.Repository.UserRepository;
import com.findme.FindMeBack.Security.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class AuthService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    @Autowired
    public AuthService(@Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, GoogleIdTokenVerifier googleIdTokenVerifier) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.googleIdTokenVerifier = googleIdTokenVerifier;
    }

    public String login(String username, String password) {
        // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public boolean verifyGoogleToken(String token) {
        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(token);
            return idToken != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = googleIdTokenVerifier.verify(token);
        return idToken.getPayload().getEmail();
    }

    public String generateJwtToken(String username) {
        return jwtUtil.generateToken(username);
    }
}
