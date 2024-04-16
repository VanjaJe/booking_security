package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotEmpty(message = "Name can not be empty.")
    private String name;

    @NotEmpty(message = "Description can not be empty")
    private String description;

    @NotNull(message = "Address can not be null.")
    @Valid
    private AddressDTO address;

    @Min(value = 0, message = "Minimum number of guests must be at least 0.")
    private int minGuests;

    @Min(value = 0, message = "Maximum number of guests must be at least 0.")
    private int maxGuests;

    @NotNull
    private AccommodationType type;

    private boolean pricePerGuest;

    private boolean automaticConfirmation;

    @NotNull
    private Host host;

    @NotNull
    private AccommodationStatus status;

    @Min(value=0)
    private int reservationDeadline;

    private ArrayList<AmenityDTO> amenities;

    private ArrayList<PricelistItem> priceList;

    private Collection<TimeSlot> freeTimeSlots;

    private double price;

    private double unitPrice;


//    public AccommodationDTO(Long id, String name, String description, AddressDTO address, int minGuests, int maxGuests, AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Host host,
//                            AccommodationStatus status, int reservationDeadline, ArrayList<AmenityDTO> amenities,
//                            ArrayList<PricelistItem> priceList,ArrayList<TimeSlot> freeTimeSlots) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.address = address;
//        this.minGuests = minGuests;
//        this.maxGuests = maxGuests;
//        this.type = type;
//        this.pricePerGuest = pricePerGuest;
//        this.automaticConfirmation = automaticConfirmation;
//        this.host = host;
//        this.status = status;
//        this.reservationDeadline = reservationDeadline;
//        this.amenities = amenities;
//        this.priceList=priceList;
//        this.freeTimeSlots=freeTimeSlots;
//    }

    public AccommodationDTO(Long id, String name, String description, AddressDTO address, int minGuests, int maxGuests, AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Host host, AccommodationStatus status, int reservationDeadline, ArrayList<AmenityDTO> amenities, ArrayList<PricelistItem> priceList, ArrayList<TimeSlot> freeTimeSlots, double price, double unitPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.pricePerGuest = pricePerGuest;
        this.automaticConfirmation = automaticConfirmation;
        this.host = host;
        this.status = status;
        this.reservationDeadline = reservationDeadline;
        this.amenities = amenities;
        this.priceList = priceList;
        this.freeTimeSlots = freeTimeSlots;
        this.price = price;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type=" + type +
                ", pricePerGuest=" + pricePerGuest +
                ", automaticConfirmation=" + automaticConfirmation +
                ", status=" + status +
                ", reservationDeadline=" + reservationDeadline +
                ", amenities=" + amenities +
                ", priceList=" + priceList +
                ", freeTimeSlots=" + freeTimeSlots +
                '}';
    }
}
