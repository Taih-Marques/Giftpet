package com.giftpet.giftpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giftpet.giftpet.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer>{

}
