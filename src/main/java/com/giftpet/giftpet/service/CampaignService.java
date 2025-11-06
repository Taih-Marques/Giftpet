package com.giftpet.giftpet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.Campaign;
import com.giftpet.giftpet.repository.CampaignRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CampaignService {
    private final CampaignRepository repository;

    public List<Campaign> findAll() {
        return repository.findAll();
    }

    public Campaign findById(int id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Campaign not found. ID: " + id));
    }
}
