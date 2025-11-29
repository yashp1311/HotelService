package com.example.hotelservice.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotelservice.exception.HotelException;
import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.repository.HotelRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
        log.info("Hotel service implementation initialized with repository.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> getAllHotels() {
        log.info("Retrieving all hotels.");
        try {
            List<Hotel> hotels = hotelRepository.findAll();
            log.debug("Successfully retrieved {} hotels.", hotels.size());
            return hotels;
        } catch (Exception e) {
            log.error("An error occurred while retrieving all hotels: ", e);
            throw new HotelException("Failed to fetch hotels: " + e.getMessage(), e);
        }
    }

    @Override
    @Cacheable(value = "hotels", key = "#id")
    @Transactional(readOnly = true)
    public Hotel getHotelById(String id) {
        log.info("Retrieving hotel with id: {}", id);
        try {
            return hotelRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("No hotel found with id: {}.", id);
                        return new HotelException("Hotel not found with id: " + id);
                    });
        } catch (Exception e) {
            log.error("An error occurred while retrieving hotel with id: {}.", id, e);
            throw new HotelException("Failed to fetch hotel: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Hotel createHotel(Hotel hotel) {
        log.info("Creating a new hotel with name: {}", hotel.getName());
        try {
            Hotel savedHotel = hotelRepository.save(hotel);
            log.info("Successfully created hotel with id: {}", savedHotel.getHotelId());
            return savedHotel;
        } catch (Exception e) {
            log.error("An error occurred while creating hotel with name: {}", hotel.getName(), e);
            throw new HotelException("Failed to create hotel: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteHotel(String id) {
        log.info("Attempting to delete hotel with id: {}", id);
        try {
            if (!hotelRepository.existsById(id)) {
                log.warn("No hotel found with id {} for deletion.", id);
                throw new HotelException("Hotel not found with id: " + id);
            }
            hotelRepository.deleteById(id);
            log.info("Successfully deleted hotel with id: {}", id);
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting hotel with id: {}", id, e);
            throw new HotelException("Failed to delete hotel: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Hotel updateHotel(String id, Hotel hotel) {
        log.info("Updating hotel with id: {}", id);
        try {
            if (!hotelRepository.existsById(id)) {
                log.warn("No hotel found with id: {}", id);
                throw new HotelException("Hotel not found with id: " + id);
            }
            hotel.setHotelId(id);
            Hotel updatedHotel = hotelRepository.save(hotel);
            log.info("Successfully updated hotel with id: {}", updatedHotel.getHotelId());
            return updatedHotel;
        } catch (DataAccessException e) {
            log.error("An error occurred while updating hotel with id: {}", id, e);
            throw new HotelException("Failed to update hotel: " + e.getMessage(), e);
        }
    }
}