package com.giftpet.giftpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giftpet.giftpet.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
     
}
