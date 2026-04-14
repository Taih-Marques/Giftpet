package com.giftpet.giftpet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giftpet.giftpet.service.ImageService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RequestMapping("/image")
@RestController
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{id}")
    public String getContent(@PathVariable Long id) {
        return imageService.findById(id).getContent();
    }
    
}
