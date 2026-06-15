package com.giftpet.giftpet.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.giftpet.giftpet.controller.dto.EventDetail;
import com.giftpet.giftpet.controller.dto.EventDetail.CampaignSummary;
import com.giftpet.giftpet.controller.dto.EventDetail.ImageSummary;
import com.giftpet.giftpet.controller.dto.NewEvent;
import com.giftpet.giftpet.model.Event;
import com.giftpet.giftpet.model.GiftCard;
import com.giftpet.giftpet.model.Image;
import com.giftpet.giftpet.repository.DonationRepository;
import com.giftpet.giftpet.repository.EventRepository;
import com.giftpet.giftpet.repository.specification.EventSpecs;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class EventService {
    private final CampaignService campaignService;
    private final EventRepository repository;
    private final ImageService imageService;
    private final GiftCardService giftCardService;
    private final DonationRepository donationRepository;

    public List<Event> findAll(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return repository.findAll(EventSpecs.hasNameOrDescription(search.trim()));
        }
        return repository.findAll();
    }

    public Event findById(int id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found. ID: " + id));
    }

    @Transactional
    public EventDetail findDetail(int id) {
        Event event = findById(id);
        List<GiftCard> giftCards = event.getGiftCards() == null ? List.of() : event.getGiftCards();
        List<ImageSummary> images = event.getImages() == null
                ? List.of()
                : event.getImages().stream()
                        .map(image -> new ImageSummary(image.getId()))
                        .toList();

        long totalCards = giftCards.size();
        long claimedCards = giftCards.stream()
                .filter(giftCard -> giftCard.getDateUsed() != null)
                .count();
        BigDecimal amountRaised = donationRepository.sumAmountByEventId(event.getId());

        return new EventDetail(
                event.getId(),
                event.getName(),
                event.getDescription(),
                new CampaignSummary(event.getCampaign().getId(), event.getCampaign().getName()),
                event.getGoal(),
                event.getDate(),
                images,
                totalCards,
                claimedCards,
                amountRaised);
    }

    @Transactional
    public Event createEvent(@Valid NewEvent newEvent, List<MultipartFile> images) {
        Event event = new Event();
        event.setName(newEvent.name());
        event.setDescription(newEvent.description());
        event.setGoal(newEvent.goal());
        event.setDate(newEvent.date());
        event.setCampaign(campaignService.findById(newEvent.campaignId()));
        List<Image> savedImages = imageService.saveAll(images);
        event.setImages(savedImages);
        event.setGiftCards(giftCardService.generateGiftCards(newEvent.giftCardQuantity(), newEvent.suggestedGiftCardValue()));
        event.getGiftCards().forEach(giftCard -> giftCard.setEvent(event));

        return repository.save(event);
    }
}
