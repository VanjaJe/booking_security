package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.dto.PricelistItemDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IAccommodationService {
    Collection<Accommodation> findAll(LocalDate begin, LocalDate end, int guestNumber, AccommodationType type, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities,String hostId);

    Accommodation findOne(Long id);
    Accommodation create(Accommodation accommodation) throws Exception;

    Accommodation update(Accommodation accommodation) throws Exception;

    Accommodation editAccommodationPricelistItem(PricelistItemDTO price, Long id);

    Accommodation editAccommodationFreeTimeSlots(TimeSlotDTO timeSlotDTO, Long idAccommodationForUpdate);

    void uploadImage(Long id, MultipartFile image) throws IOException;

    List<String> getImages(Long id) throws IOException;

    void delete(Long id);

    double calculatePriceForAccommodation(Long id, int guestNumber, Date begin, Date end);

    Accommodation accept(Accommodation accommodation);

    Accommodation decline(Accommodation accommodation);

    Accommodation changeFreeTimeSlotsAcceptingReservation(Long accommodationId, TimeSlotDTO reservationTimeSlot);
    Accommodation changeFreeTimeSlotsCancelingReservation(Long accommodationId, TimeSlotDTO reservationTimeSlot);

    Accommodation updateRequestApproval(Accommodation accommodation);
}
