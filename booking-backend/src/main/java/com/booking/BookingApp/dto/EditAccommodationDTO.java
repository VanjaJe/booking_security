package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.PricelistItem;
import com.booking.BookingApp.domain.TimeSlot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class EditAccommodationDTO {
//    private boolean pricePerGuest;
//    private boolean automaticConfirmation;
//    private int reservationDeadline;
    private ArrayList<PricelistItem> priceList;
    private ArrayList<TimeSlot> freeTimeSlots;

    public EditAccommodationDTO(ArrayList<PricelistItem> priceList,ArrayList<TimeSlot> freeTimeSlots) {
//        this.pricePerGuest = pricePerGuest;
//        this.automaticConfirmation = automaticConfirmation;
//        this.reservationDeadline = reservationDeadline;
        this.priceList=priceList;
        this.freeTimeSlots=freeTimeSlots;
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
//                ", pricePerGuest=" + pricePerGuest +
//                ", automaticConfirmation=" + automaticConfirmation +
//                ", reservationDeadline=" + reservationDeadline +
                ", priceList=" + priceList +
                ", freeTimeSlots=" + freeTimeSlots +
                '}';
    }
}
