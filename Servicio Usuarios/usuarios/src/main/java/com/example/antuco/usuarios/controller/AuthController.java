package com.example.antuco.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.usuarios.dto.LoginRequest;
import com.example.antuco.usuarios.dto.RegistroRequest;
import com.example.antuco.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<String> register(@RequestBody RegistroRequest registroRequest) {
        try {
            usuarioService.registrar(registroRequest);
            return ResponseEntity.ok("Usuario registrado!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Create a token with the username and password from the request
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getClave()
            );

            // 3. Use the AuthenticationManager to authenticate
            // This calls your CustomUserDetailsService internally
            Authentication authentication = authenticationManager.authenticate(authToken);

            // If we get here, login was successful!
            // In a real app, you would generate a JWT Token here and return it.
            // For now, we just return a success message.
            return ResponseEntity.ok("Sesion iniciada: " + authentication.getName());

        } catch (AuthenticationException e) {
            // This catches BadCredentialsException (wrong password) etc.
            return ResponseEntity.status(401).body("Fallo el login: " + e.getMessage());
        }
    }

}
