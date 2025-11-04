package com.giftpet.giftpet.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.Usuario;
import com.giftpet.giftpet.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private UsuarioRepository repository;
    private PasswordEncoder encoder;

    public Usuario criarUsuario(String nome, String email, String senha, boolean admin) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(encoder.encode(senha));
        usuario.setAdmin(admin);

        return repository.save(usuario);
    }
}
