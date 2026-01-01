package com.example.hotelservice.model;

import java.io.Serializable;
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
@Table(name = "ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rating_id", updatable = false, nullable = false, length = 30)
    private String ratingId;

    @Column(name = "hotel_id")
    private String hotelId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.ratingId == null) {
            this.ratingId = UUID.randomUUID().toString().length() > 30 ? UUID.randomUUID().toString().substring(0, 30)
                    : UUID.randomUUID().toString();
        }
    }
}