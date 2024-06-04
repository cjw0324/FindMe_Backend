package com.findme.FindMeBack.Controller;

import com.findme.FindMeBack.Entity.User;
import com.findme.FindMeBack.Service.AuthService;
import com.findme.FindMeBack.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) throws GeneralSecurityException, IOException {
        String token = request.get("token");
        System.out.println("token = " + token);
        if (authService.verifyGoogleToken(token)) {
            System.out.println("AuthController.googleLogin_1");
            String username = authService.getUsernameFromToken(token);
            if (username != null) {
                System.out.println("AuthController.googleLogin_2");
                String jwtToken = authService.generateJwtToken(username);

                Map<String, String> response = new HashMap<>();
                response.put("token", jwtToken);
                response.put("message", "Login successful");
                System.out.println("AuthController.googleLogin_999");
                return ResponseEntity.ok(response);
            } else {
                System.out.println("AuthController.googleLogin_3");
                return ResponseEntity.status(401).body("Failed to get username from token");
            }
        } else {
            System.out.println("AuthController.googleLogin_4");
            return ResponseEntity.status(401).body("Invalid Google token");
        }
    }

}
