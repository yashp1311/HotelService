package com.example.hotelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelservice.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
}