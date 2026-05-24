package com.giftpet.giftpet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.Campaign;
import com.giftpet.giftpet.repository.CampaignRepository;
import com.giftpet.giftpet.repository.specification.CampaignSpecs;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CampaignService {
    private final CampaignRepository repository;

    public List<Campaign> findAll(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return repository.findAll(CampaignSpecs.hasNameOrDescription(search.trim()));
        }
        return repository.findAll();
    }

    public Campaign findById(int id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Campaign not found. ID: " + id));
    }
}
