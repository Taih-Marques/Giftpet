package com.giftpet.giftpet.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record EventDetail(
        Integer id,
        String name,
        String description,
        CampaignSummary campaign,
        BigDecimal goal,
        LocalDate date,
        List<ImageSummary> images,
        long totalCards,
        long claimedCards,
        BigDecimal amountRaised) {

    public record CampaignSummary(Integer id, String name) {
    }

    public record ImageSummary(String id) {
    }
}
