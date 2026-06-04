package com.giftpet.giftpet.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.giftpet.giftpet.model.GiftCard;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, String>, JpaSpecificationExecutor<GiftCard> {

    @Query("select giftCard.code from GiftCard giftCard where giftCard.code in :codes")
    List<String> findExistingCodes(@Param("codes") Collection<String> codes);
}
