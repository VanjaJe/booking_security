package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.TimeSlotDTO;

import java.sql.Time;
import java.util.Collection;

public interface IAvailabilityService {

    boolean checkFreeTimeSlots(TimeSlot timeSlot, Accommodation accommodation);

    Collection<TimeSlot> updateFreeTimeSlots(TimeSlotDTO newTimeSlot, Collection<TimeSlot> timeSlots);

}
