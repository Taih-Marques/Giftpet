package com.giftpet.giftpet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String donorName;

    @NotBlank
    @CPF
    private String donorCPF;

    @NotBlank
    @Email
    private String donorEmail;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    @Column(unique = true)
    private String transactionId;

    @NotNull
    private LocalDateTime createdAt;

    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Event event;

    @JoinColumn(name = "giftcard_code", referencedColumnName = "code", nullable = false)
    @ManyToOne
    private GiftCard giftCard;
}