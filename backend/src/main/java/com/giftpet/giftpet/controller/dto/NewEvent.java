package com.giftpet.giftpet.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record NewEvent(
        @NotBlank(message = "O nome do evento é obrigatório.")
        @Size(max = 250, message = "O nome do evento deve ter no máximo 250 caracteres.")
        String name,

        @NotBlank(message = "A descrição do evento é obrigatória.")
        @Size(max = 2000, message = "A descrição do evento deve ter no máximo 2000 caracteres.")
        String description,

        @NotNull(message = "O ID da campanha é obrigatório.")
        Integer campaignId,

        @NotNull(message = "A meta do evento é obrigatória.")
        BigDecimal goal,

        @NotNull(message = "A data do evento é obrigatória.")
        LocalDate date,

        @NotNull(message = "A quantidade de giftcards é obrigatória.")
        @Positive(message = "A quantidade de giftcards deve ser um número positivo.")
        Integer giftCardQuantity,

        @NotNull(message = "O valor sugerido para os giftcards é obrigatório.")
        @Positive(message = "O valor sugerido para os giftcards deve ser um número positivo.")
        BigDecimal suggestedGiftCardValue) {
    
}
