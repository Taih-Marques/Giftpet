package com.giftpet.giftpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giftpet.giftpet.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>  {

}
