package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;

import java.util.Collection;

public interface IReportService {

    Collection<Report> findAllByTimeSlot(String hostId,TimeSlot timeslot);
    Report findAnnualByAccommodation(String accommodationName,  int year);

}
