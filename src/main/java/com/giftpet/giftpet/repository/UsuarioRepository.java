package com.giftpet.giftpet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giftpet.giftpet.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
