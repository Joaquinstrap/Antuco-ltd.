package com.example.antuco.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.usuarios.dto.LoginRequest;
import com.example.antuco.usuarios.dto.RegistroRequest;
import com.example.antuco.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Usuarios", description = "BD/autenticador de usuarios")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    @Operation(summary = "Crear registro", description = "Registra al usuario, su nombre y el rol (usuario, administrador)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro hecho correctamente", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Se ha escrito mal un parametro al enviar.", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> register(@RequestBody RegistroRequest registroRequest) {
        try {
            usuarioService.registrar(registroRequest);
            return ResponseEntity.ok("Usuario registrado!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Inicia sesion y verifica si los datos son correctos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sesion iniciada.", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Fallo el login. Credenciales incorrectas", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Create a token with the username and password from the request
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getClave()
            );

            Authentication authentication = authenticationManager.authenticate(authToken);

            return ResponseEntity.ok("Sesion iniciada: " + authentication.getName());

        } catch (AuthenticationException e) {

            return ResponseEntity.status(401).body("Fallo el login: " + e.getMessage());
        }
    }

}
