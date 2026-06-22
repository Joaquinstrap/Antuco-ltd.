package com.example.antuco.autenticacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.antuco.autenticacion.model.Credencial;
import com.example.antuco.autenticacion.repository.CredencialRepository;
import com.example.antuco.autenticacion.util.JwtUtil;

@Service
public class AutenticacionService {

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Después (Copia y pega esto):
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Registrar credenciales
    public void registrar(String username, String passwordRaw) {
        if (credencialRepository.findByUsername(username) != null) {
            throw new RuntimeException("El usuario ya existe en Autenticación");
        }
        Credencial cred = new Credencial();
        cred.setUsername(username);
        cred.setPassword(passwordRaw);
        cred.encriptarPassword(); 
        credencialRepository.save(cred);
    }

    // Login y Verificación
    public String login(String username, String passwordRaw) {
        Credencial cred = credencialRepository.findByUsername(username);
        if (cred == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (passwordEncoder.matches(passwordRaw, cred.getPassword())) {
            // Si la clave es correcta, generamos el TOKEN
            return jwtUtil.generarToken(username);
        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }
}