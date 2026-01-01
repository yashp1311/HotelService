package com.example.hotelservice.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "hotel_id", updatable = false, nullable = false, length = 30)
    private String hotelId;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "ratings")
    private List<Rating> ratings;

    @PrePersist
    protected void onCreate() {
        if (this.hotelId == null) {
            this.hotelId = UUID.randomUUID().toString().length() > 30 ? UUID.randomUUID().toString().substring(0, 30)
                    : UUID.randomUUID().toString();
        }
    }
}