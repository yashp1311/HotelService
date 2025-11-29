package com.example.hotelservice.service;

import java.util.List;

import com.example.hotelservice.model.Hotel;

public interface HotelService {

    public List<Hotel> getAllHotels();

    public Hotel getHotelById(String id);

    public Hotel createHotel(Hotel hotel);

    public void deleteHotel(String id);

    public Hotel updateHotel(String id, Hotel hotel);
}