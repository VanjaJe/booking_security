package com.booking.BookingApp.dto;


import com.booking.BookingApp.domain.TimeSlot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PricelistItemDTO {
//    private Long id;
    private TimeSlotDTO timeSlot;
    private double price;

    public PricelistItemDTO(TimeSlotDTO timeSlot, double price) {
//        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
    }

    @Override
    public String toString() {
        return "PricelistItemDTO{" +
                "timeSlot=" + timeSlot +
                ", price=" + price +
                '}';
    }
}
