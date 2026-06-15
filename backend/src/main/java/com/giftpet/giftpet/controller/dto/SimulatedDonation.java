package com.giftpet.giftpet.controller.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record SimulatedDonation(
        @NotBlank(message = "O nome completo é obrigatório.")
        String fullName,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail informado é inválido.")
        String email,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O CPF informado é inválido.")
        String cpf,

        @NotNull(message = "O valor da doação é obrigatório.")
        @Positive(message = "O valor da doação deve ser maior que zero.")
        BigDecimal amount,

        @NotBlank(message = "O código do gift card é obrigatório.")
        @Pattern(regexp = "(?i)^GP[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$", message = "O código do gift card é inválido.")
        String giftCardCode,

        @NotNull(message = "O ID do evento é obrigatório.")
        Integer eventId) {
}
