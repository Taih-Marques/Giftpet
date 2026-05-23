package com.giftpet.giftpet.service;

import java.util.Base64;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.Image;
import com.giftpet.giftpet.repository.ImageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image findById(@NonNull String id) {
         return imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image not found. ID: " + id));
    }

    public byte[] getImageBytes(@NonNull String id) {
        try {
            UUID.fromString(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("ID must be a valid UUID.");
        }
        String base64 = findById(id).getContent();
        return Base64.getDecoder().decode(base64);
    }
}