package com.example.hotelservice.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class HotelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HotelException(String message, Throwable cause) {
        super(message, cause);
    }

    public HotelException(String message) {
        super(message);
    }

    public HotelException(Throwable cause) {
        super(cause);
    }
}