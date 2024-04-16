package com.booking.BookingApp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@Entity
public class FavoriteAccommodation {
//    @Id
//    @GeneratedValue
    private Long id;
    private Long guestId;
    private Long accommodationId;

    public FavoriteAccommodation(Long id, Long guestId, Long accommodationId) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "FavoriteAccommodation{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", accommodationId=" + accommodationId +
                '}';
    }
}
