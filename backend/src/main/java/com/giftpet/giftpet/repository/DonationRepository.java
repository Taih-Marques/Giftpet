package com.giftpet.giftpet.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giftpet.giftpet.model.Donation;
 
public interface DonationRepository extends JpaRepository<Donation, Integer>, JpaSpecificationExecutor<Donation> {

    @Query("select coalesce(sum(donation.amount), 0) from Donation donation where donation.event.id = :eventId")
    BigDecimal sumAmountByEventId(@Param("eventId") Integer eventId);
}
