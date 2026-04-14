package com.giftpet.giftpet.service;

import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.Image;
import com.giftpet.giftpet.repository.ImageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image findById(Long id) {
         return imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image not found. ID: " + id));
    }
}