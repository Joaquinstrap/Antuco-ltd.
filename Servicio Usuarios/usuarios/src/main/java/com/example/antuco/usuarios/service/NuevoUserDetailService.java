package com.example.antuco.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.antuco.usuarios.model.Usuario;
import com.example.antuco.usuarios.repository.UsuarioRepository;

@Service
public class NuevoUserDetailService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No se encuentra usuario"));
        return User.builder()
            .username(usuario.getUsername())
            .password(usuario.getClave())
            .roles(usuario.getRol())
            .build();
    }
    
}
