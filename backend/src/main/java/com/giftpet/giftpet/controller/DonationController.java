package com.giftpet.giftpet.controller;

import com.giftpet.giftpet.service.DonationService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giftpet.giftpet.controller.dto.SimulatedDonation;

@AllArgsConstructor
@RestController
@RequestMapping("/donation")
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> simulateDonation(@RequestBody SimulatedDonation request) {
        donationService.simulateDonation(request);
        return ResponseEntity.noContent().build();
    }
}
