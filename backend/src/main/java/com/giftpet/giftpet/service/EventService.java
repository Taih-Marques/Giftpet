package com.giftpet.giftpet.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.giftpet.giftpet.controller.dto.NewEvent;
import com.giftpet.giftpet.model.Event;
import com.giftpet.giftpet.model.Image;
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
    public Event createEvent(@Valid NewEvent newEvent, List<MultipartFile> images) {
        Event event = new Event();
        event.setName(newEvent.name());
        event.setDescription(newEvent.description());
        event.setGoal(newEvent.goal());
        event.setDate(newEvent.date());
        event.setCampaign(campaignService.findById(newEvent.campaignId()));
        List<Image> savedImages = imageService.saveAll(images);
        event.setImages(savedImages);

        // TODO: Implementar geração de giftcards
        // generateGiftCardsForEvent(event, newEvent.giftCardQuantity(), newEvent.suggestedGiftCardValue());

        return repository.save(event);
    }
}
