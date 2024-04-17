package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
@TestPropertySource("classpath:application-test.properties")
public class RequestServiceTest {

    @Mock
    RequestRepository requestRepository;

    @Mock
    AccommodationService accommodationService;

    @Mock
    AvailabilityService availabilityService;

    @InjectMocks
    RequestService requestService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRequest_PastDate() throws Exception {
        TimeSlot timeSlot=getDateInPast();
        Accommodation accommodation=new Accommodation();
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    public void createRequest_EndBeforeStartDate() throws Exception {
        TimeSlot timeSlot=getEndBeforeStartDate();
        Accommodation accommodation=new Accommodation();
        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    public void createRequest_InvalidDates() throws Exception {
        TimeSlot timeSlot=getInvalidDates();
        Accommodation accommodation=new Accommodation();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(false);

        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }
    @ParameterizedTest
    @CsvSource(value ={
            "1",
            "10"
    })
    public void createRequest_InvalidGuestNumber(int guestNum) throws Exception {
        TimeSlot timeSlot=getValidDates();

        Accommodation accommodation=new Accommodation();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(2);
        accommodation.setMaxGuests(5);

        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);

        Request request = new Request(1L, timeSlot, 200, null, accommodation, guestNum, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }
    @ParameterizedTest
    @CsvSource(value ={
            "2",
            "5",
            "3"
    })
    public void createRequest_PriceNotDefined(int guestNum) throws Exception {
        TimeSlot timeSlot=getValidDates();
        Date startDate=Date.from(timeSlot.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(timeSlot.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Accommodation accommodation=new Accommodation();
        accommodation.setId(1L);
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(2);
        accommodation.setMaxGuests(5);

        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);
        when(accommodationService.calculatePriceForAccommodation(1L,guestNum,startDate,endDate)).thenReturn(0.0);
        Request request = new Request(1L, timeSlot, 0, null, accommodation, guestNum, RequestStatus.PENDING, false);

        Request result=requestService.create(request);
        assertNull(result);

        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verify(accommodationService).calculatePriceForAccommodation(1L,guestNum,startDate,endDate);

        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    public void createRequest_PricesDiffer() throws Exception {
        TimeSlot timeSlot=getValidDates();
        Date startDate=Date.from(timeSlot.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(timeSlot.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Accommodation accommodation=new Accommodation();
        accommodation.setId(1L);
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);

        Request request = new Request(1L, timeSlot, 1000.0, null, accommodation, 3, RequestStatus.PENDING, false);


        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);
        when(accommodationService.calculatePriceForAccommodation(1L,3,startDate,endDate)).thenReturn(2000.0);

        Request result=requestService.create(request);
        assertNull(result);

        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verify(accommodationService,times(2)).calculatePriceForAccommodation(1L,3,startDate,endDate);

        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }

    @Test
    public void createRequest_Valid() throws Exception {
        TimeSlot timeSlot=getValidDates();
        Date startDate=Date.from(timeSlot.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(timeSlot.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Accommodation accommodation=new Accommodation();
        accommodation.setId(1L);
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);

        Request request = new Request(1L, timeSlot, 2000.0, null, accommodation, 3, RequestStatus.PENDING, false);

        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);
        when(accommodationService.calculatePriceForAccommodation(1L,3,startDate,endDate)).thenReturn(2000.0);
        when(requestRepository.save(request)).thenReturn(request);

        Request result=requestService.create(request);
        assertNotNull(result);
        assertEquals(result,request);

        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verify(accommodationService,times(2)).calculatePriceForAccommodation(1L,3,startDate,endDate);
        verify(requestRepository).save(request);

        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(requestRepository);
    }


    private TimeSlot getInvalidDates() { //not in the free time slots
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(20);
        LocalDate endDate = now.plusDays(30);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getValidDates() {  //free time slots will be this
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(10);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getDateInPast() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(7);
        LocalDate endDate = now.plusDays(3);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getEndBeforeStartDate() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(1);

        return new TimeSlot(startDate, endDate);
    }

}