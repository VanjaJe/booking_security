package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Address;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.service.interfaces.IRequestService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RequestService implements IRequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Override
    public Collection<Request> findAll(RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {
        if(status!=null && begin!=null && end!=null && accommodationName!=null){
            Accommodation accommodation = accommodationRepository.findByName(accommodationName);
            return requestRepository.findAllActiveReservationsForAccommodation(LocalDate.now(),accommodation);
        }
        if(status!=null){
            return requestRepository.findByStatus(status);
        }
        return requestRepository.findAll();
    }

    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Request> findByHostId(String id,RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {
        return requestRepository.findAllForHost(id, status, begin, end, accommodationName);
    }

    public Collection<Request> findAllRequestForHost(RequestStatus status, String id) {
        return requestRepository.findAllByStatusAndGuest_AccountUsername(status, id);
    }

    @Override
    public Collection<Request> findByHost(String id) {
        return requestRepository.findByAccommodation_Host_AccountUsername(id);
    }

    public Collection<Request> findByGuestId(String id, RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {
        return requestRepository.findAllForGuest(id, status,  begin, end, accommodationName);
    }

    public Collection<Request> findByAccommodationId(Long id) {
        return  requestRepository.findByAccommodation_Id(id);
    }

    @Override
    public Request create(Request request) throws Exception{

        if(request.getTimeSlot().getStartDate().isBefore(LocalDate.now())){
            return null;
        }
        if(request.getTimeSlot().getEndDate().isBefore(request.getTimeSlot().getStartDate())){
            return null;
        }
        if(!availabilityService.checkFreeTimeSlots(request.getTimeSlot(),request.getAccommodation())){
            return null;
        }
        if(!(request.getGuestNumber()<=request.getAccommodation().getMaxGuests() && request.getGuestNumber()>=request.getAccommodation().getMinGuests())){
            return null;
        }
        Date startDate=Date.from(request.getTimeSlot().getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(request.getTimeSlot().getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(accommodationService.calculatePriceForAccommodation(request.getAccommodation().getId(),request.getGuestNumber(),startDate,endDate)==0
            ||accommodationService.calculatePriceForAccommodation(request.getAccommodation().getId(),request.getGuestNumber(),startDate,endDate)!=request.getPrice()){
            return null;
        }

        return requestRepository.save(request);
    }

    @Override
    public Request update(Request requestForUpdate, Request request) {
        requestForUpdate.setAccommodation(request.getAccommodation());
        requestForUpdate.setTimeSlot(request.getTimeSlot());
        requestForUpdate.setGuestNumber(request.getGuestNumber());
        requestForUpdate.setStatus(request.getStatus());
        requestForUpdate.setPrice(request.getPrice());
        requestForUpdate.setGuest(request.getGuest());
        return requestRepository.save(requestForUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Request request = requestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setDeleted(true);
            requestRepository.save(request);
        }
    }

    @Override
    public int findCancellations(String id) {
        Collection<Request> requests=requestRepository.findAllForGuest(id,RequestStatus.CANCELLED,null,null,null);
        return requests.size();
    }

    @Override
    public Request accept(Request request) {
        Collection<Request> requests = requestRepository.findAllByAccommodationAndTimeSlot(request.getAccommodation()
                ,request.getTimeSlot().getStartDate(),request.getTimeSlot().getEndDate());
        for(Request _request:requests){
            _request.setStatus(RequestStatus.DENIED);
            requestRepository.save(_request);
        }
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(request.getTimeSlot().getStartDate(),request.getTimeSlot().getEndDate());
        Accommodation accommodation = accommodationService.changeFreeTimeSlotsAcceptingReservation(request.getAccommodation().getId(),timeSlotDTO);
        request.setStatus(RequestStatus.ACCEPTED);
        return requestRepository.save(request);
    }

    @Override
    public Request deny(Request request) {
        request.setStatus(RequestStatus.DENIED);
        return requestRepository.save(request);
    }

    @Override
    public Request cancel(Request request) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(request.getTimeSlot().getStartDate(),request.getTimeSlot().getEndDate());
        accommodationService.changeFreeTimeSlotsCancelingReservation(request.getAccommodation().getId(),timeSlotDTO);
        request.setStatus(RequestStatus.CANCELLED);
        return requestRepository.save(request);
    }

    @Override
    public Collection<Request> findReservationsByYear(String accommodationName,int year) {
        return requestRepository.findAllByAccommodationNameAndYear(accommodationName,year);
    }
}
