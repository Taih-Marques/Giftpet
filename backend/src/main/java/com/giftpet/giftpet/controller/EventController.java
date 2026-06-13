package com.giftpet.giftpet.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.giftpet.giftpet.controller.dto.NewEvent;
import com.giftpet.giftpet.model.Event;
import com.giftpet.giftpet.service.EventService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Event> findAll(@RequestParam(required = false) String search) {
        return service.findAll(search);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public Event findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CreateEventResponse> createEvent(@RequestPart("event") NewEvent event,
            @RequestPart(value = "images") List<MultipartFile> images) {
        Event createdEvent = service.createEvent(event, images);
        URI location = URI.create("/event/" + createdEvent.getId());

        return ResponseEntity.created(location).body(new CreateEventResponse(createdEvent.getId()));
    }

    private record CreateEventResponse(Integer id) {}
}
