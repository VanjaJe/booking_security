package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.service.interfaces.IAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AvailabilityService implements IAvailabilityService {

    @Autowired
    RequestRepository requestRepository;

    @Override
    public boolean checkFreeTimeSlots(TimeSlot newTimeSlot, Accommodation accommodation) {
        Collection<TimeSlot>freeTimeSlots=accommodation.getFreeTimeSlots();
        for(TimeSlot timeSlot:freeTimeSlots){
            if((timeSlot.getStartDate().isBefore(newTimeSlot.getStartDate()) || timeSlot.getStartDate().equals(newTimeSlot.getStartDate()) ) &&
                    (timeSlot.getEndDate().isAfter(newTimeSlot.getEndDate()) || timeSlot.getEndDate().equals(newTimeSlot.getEndDate())) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<TimeSlot> updateFreeTimeSlots(TimeSlotDTO newTimeSlot, Collection<TimeSlot> timeSlots) {
        System.out.println("wtf" );
        boolean check=false;
        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.getStartDate().isBefore(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isAfter(newTimeSlot.getEndDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                System.out.println(timeSlot);
                check=true;
//                return timeSlot;
            }

            if (timeSlot.getStartDate().isAfter(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isAfter(newTimeSlot.getEndDate()) && timeSlot.getStartDate().isBefore(newTimeSlot.getEndDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                System.out.println(timeSlot);
                check=true;
//                return timeSlot;
            }

            if (timeSlot.getStartDate().isBefore(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isBefore(newTimeSlot.getEndDate()) && timeSlot.getEndDate().isAfter(newTimeSlot.getStartDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                System.out.println(timeSlot);
                check=true;
//                return timeSlot;
            }

            if (timeSlot.getStartDate().isAfter(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isBefore(newTimeSlot.getEndDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                System.out.println(timeSlot);
                check=true;
//                return timeSlot;
            }
            if (timeSlot.getStartDate().isEqual(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isEqual(newTimeSlot.getEndDate())) {
                check=true;
            }
        }
        if (!check) {
            System.out.println("DOOSAOOOOOO");
            TimeSlot newFreeTimeSlot = new TimeSlot(newTimeSlot.getStartDate(), newTimeSlot.getEndDate(),false);
            timeSlots.add(newFreeTimeSlot);
            System.out.println(newFreeTimeSlot);
        }
        System.out.println(timeSlots);
        return timeSlots;
    }


    public boolean reservationOverlaps(TimeSlotDTO timslot, Long accommodationId) {
        Collection<Request> requests = requestRepository.findByStatusAndAccommodation_Id(RequestStatus.ACCEPTED, accommodationId);
        for (Request request : requests) {
            LocalDate requestStart = request.getTimeSlot().getStartDate();
            LocalDate requestEnd = request.getTimeSlot().getEndDate();
            if (requestStart.isAfter(timslot.getStartDate()) && requestEnd.isBefore(timslot.getEndDate())) {
                return true;
            }
            if (requestStart.isBefore(timslot.getStartDate()) && requestEnd.isBefore(timslot.getEndDate()) &&
                    timslot.getStartDate().isBefore(requestEnd)) {
                return true;
            }
            if (requestStart.isAfter(timslot.getStartDate()) && requestEnd.isAfter(timslot.getEndDate()) &&
                    requestStart.isBefore(timslot.getEndDate())) {
                return true;
            }
            if (requestStart.isBefore(timslot.getStartDate()) && requestEnd.isAfter(timslot.getEndDate())) {
                return true;
            }
        }
        return false;
    }

}
