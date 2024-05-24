package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import org.springframework.cglib.core.Local;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface IRequestService {

    Collection<Request> findAll(RequestStatus status, LocalDate begin, LocalDate end, String accommodationName);

    Request findById(Long id);

    Collection<Request> findByHostId(String id,RequestStatus status, LocalDate begin, LocalDate end, String accommodationName);

    Collection<Request> findByHost(String id);

    Collection<Request> findByGuestId(String id, RequestStatus status, LocalDate begin, LocalDate end, String accommodationName);

    public Collection<Request> findReservationsByYear(String accommodationName,int year);

//    Collection<Request> findReservationByGuestId(Long id, RequestStatus status);
//
//    Collection<Request> findWaitingRequest(Long id);
//
//    Request findByAccommodationId(Long id);

    Request create(Request request) throws Exception;

    Request update(Request requestForUpdate, Request request);

    void delete(Long id);
    int findCancellations(String id);

    Request accept(Request request);

    Request deny(Request request);

    Request cancel(Request request);
}
