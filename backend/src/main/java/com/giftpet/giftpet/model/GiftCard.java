package com.giftpet.giftpet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giftcard")
public class GiftCard {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    @NotBlank
    private String code;

    @Column(name = "suggested_amount")
    @PositiveOrZero
    private BigDecimal suggestedAmount;

    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Event event;

    @Column(name = "date_used")
    private LocalDateTime dateUsed;
}
