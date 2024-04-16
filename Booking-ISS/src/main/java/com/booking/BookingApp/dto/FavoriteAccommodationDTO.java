package com.booking.BookingApp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteAccommodationDTO {
    private Long id;
    private Long guestId;
    private Long accommodationId;

    public FavoriteAccommodationDTO(Long id, Long guestId, Long accommodationId) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "FavoriteAccommodationDTO{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", accommodationId=" + accommodationId +
                '}';
    }
}
