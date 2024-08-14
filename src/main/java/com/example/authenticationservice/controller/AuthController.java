package com.example.authenticationservice.controller;

import com.example.authenticationservice.model.User;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> generateToken(@RequestParam int studentId) {
        return userRepository.findById(studentId)
                .map(user -> {
                    String token = jwtTokenProvider.generateToken(user.getId());
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(new AuthResponse("Student not found")));
    }

    @GetMapping("/get-all-students")
    public List<User> getAllStudents() {

        return userRepository.findAll();
    }
}

class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }
}
