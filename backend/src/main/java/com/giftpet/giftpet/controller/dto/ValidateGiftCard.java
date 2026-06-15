package com.giftpet.giftpet.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ValidateGiftCard(
    @NotBlank(message = "O código do gift card é obrigatório.")
    @Pattern(regexp = "(?i)^GP[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$", message = "O código do gift card é inválido.")
    String code,
    @NotNull(message = "O ID do evento é obrigatório.")
    Integer eventId) {
}
