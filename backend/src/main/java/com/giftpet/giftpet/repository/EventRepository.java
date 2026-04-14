package com.giftpet.giftpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giftpet.giftpet.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{

}
