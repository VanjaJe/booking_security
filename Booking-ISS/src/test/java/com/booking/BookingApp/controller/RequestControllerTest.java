package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.dto.UserCredentialsDTO;
import com.booking.BookingApp.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class RequestControllerTest {


    private String tokenGuest;


    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void login(){
            ResponseEntity<UserTokenState> responseEntityGuest =
                    restTemplate.postForEntity("/api/users/login",
                            new UserCredentialsDTO("mika@example.com", "123"),
                            UserTokenState.class);
            tokenGuest = responseEntityGuest.getBody().getAccessToken();
    }

    @Test
    public void validRequest() {
        TimeSlot timeSlot=getValidDates();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        HttpHeaders headers = new HttpHeaders();
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  3);
        ;
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void invalidAccommodationRequest() {
        TimeSlot timeSlot=getValidDates();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(11L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void invalidGuestNumberRequest() {
        TimeSlot timeSlot=getValidDates();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  33);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void datesInPastRequest() {
        TimeSlot timeSlot=getDateInPast();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void startDateBeforeEndRequest() {
        TimeSlot timeSlot=getEndBeforeStartDate();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void datesNotFreeRequest() {
        TimeSlot timeSlot=getInvalidDates();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void priceDoesntExistRequest() {
        TimeSlot timeSlot=getDatesWithoutPrice();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getDatesWithoutPrice());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3000, accommodation,RequestStatus.PENDING,  3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void pricesDontMatchRequest() {
        TimeSlot timeSlot=getValidDates();

        AccommodationDTO accommodation=new AccommodationDTO();

        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);
        RequestDTO requestDTO =new RequestDTO(1L, timeSlot, 3, accommodation,RequestStatus.PENDING,  3);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);

        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO resultDto = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }



    private TimeSlot getDatesWithoutPrice() {  //dates from database without prices

        LocalDate startDate=LocalDate.of(2024,5,20);
        LocalDate endDate=LocalDate.of(2024,5,30);

        return new TimeSlot(startDate, endDate);
    }

    private TimeSlot getValidDates() {  //dates from database

        LocalDate startDate=LocalDate.of(2024,1,20);
        LocalDate endDate=LocalDate.of(2024,1,30);

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
    private TimeSlot getInvalidDates() { //not in the free time slots
        LocalDate startDate=LocalDate.of(2024,3,20);
        LocalDate endDate=LocalDate.of(2024,3,30);

        return new TimeSlot(startDate, endDate);

    }


}
