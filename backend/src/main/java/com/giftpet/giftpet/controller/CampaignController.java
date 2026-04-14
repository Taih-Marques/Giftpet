package com.giftpet.giftpet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giftpet.giftpet.model.Campaign;
import com.giftpet.giftpet.service.CampaignService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/campaign")
@AllArgsConstructor
public class CampaignController {

    private final CampaignService service;

    @GetMapping
    public List<Campaign> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Campaign findById(@PathVariable int id) {
        return service.findById(id);
    }
}
