package com.giftpet.giftpet.config;

import org.springframework.stereotype.Component;

import com.giftpet.giftpet.service.UsuarioService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BaseTeste {
    private UsuarioService service;

    @PostConstruct
    public void inicializar() {
        criarUsuarios();
    }

    public void criarUsuarios() {
        service.criarUsuario("Admin", "admin@giftpet.org", "123", true);
        service.criarUsuario("Usuario", "usuario@exemplo.com", "123", false);
    }
}
