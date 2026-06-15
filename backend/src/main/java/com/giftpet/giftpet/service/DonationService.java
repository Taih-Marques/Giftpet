package com.giftpet.giftpet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.giftpet.giftpet.controller.dto.SimulatedDonation;
import com.giftpet.giftpet.model.Donation;
import com.giftpet.giftpet.model.Event;
import com.giftpet.giftpet.model.GiftCard;
import com.giftpet.giftpet.repository.DonationRepository;
import com.giftpet.giftpet.repository.EventRepository;
import com.giftpet.giftpet.repository.GiftCardRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Validated
public class DonationService {

    private final EventService eventService;
    private final GiftCardService giftCardService;
    private final DonationRepository donationRepository;

    @Transactional
    public Donation simulateDonation(@Valid SimulatedDonation request) {
        Event event = eventService.findById(request.eventId());

        GiftCard giftCard = giftCardService.findByCode(request.giftCardCode());

        if (!giftCard.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("Gift card não pertence ao evento informado.");
        }

        if (giftCard.getDateUsed() != null) {
            throw new IllegalArgumentException("Gift card já utilizado.");
        }

        Donation donation = new Donation();
        donation.setEvent(event);
        donation.setDonorName(request.fullName());
        donation.setDonorEmail(request.email());
        donation.setDonorCPF(request.cpf());
        donation.setAmount(request.amount());
        donation.setTransactionId("SIM-" + UUID.randomUUID());
        donation.setCreatedAt(LocalDateTime.now());
        
        giftCard = giftCardService.redeem(giftCard);
        donation.setGiftCard(giftCard);
        return donationRepository.save(donation);
    }
}
