package com.giftpet.giftpet.service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<Image> saveAll(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }
        return images.stream().map(image -> {
            try {
                String base64 = Base64.getEncoder().encodeToString(image.getBytes());
                Image img = new Image();
                img.setContent(base64);
                return imageRepository.save(img);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
            }
        }).toList();
    }
}