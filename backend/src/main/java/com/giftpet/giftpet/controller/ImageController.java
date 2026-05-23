package com.giftpet.giftpet.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

    @GetMapping("/content/{id}")
    public ResponseEntity<byte[]> getContent(@PathVariable @NonNull String id) {
        byte[] imageBytes = imageService.getImageBytes(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
