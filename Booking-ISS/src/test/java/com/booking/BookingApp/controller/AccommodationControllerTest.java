package com.booking.BookingApp.controller;
import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.*;
import com.booking.BookingApp.service.AccommodationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AccommodationControllerTest {
    private HttpHeaders headers = new HttpHeaders();

    private String tokenHost;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AccommodationService accommodationService;

    @BeforeEach
    public void login(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new UserCredentialsDTO("zika@example.com", "123"),
                        UserTokenState.class);
        tokenHost = responseEntity.getBody().getAccessToken();
    }

    @Test
    public void validEditFreeTimeSlot() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-03-20");
        LocalDate endDate = LocalDate.parse("2024-03-27");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<TimeSlot> timeSlotList=responseEntity.getBody().getFreeTimeSlots().stream().toList();
        assertEquals(2, responseEntity.getBody().getFreeTimeSlots().size());
        assertEquals(LocalDate.parse("2024-01-20"), timeSlotList.get(0).getStartDate());
        assertEquals(LocalDate.parse("2024-01-30"), timeSlotList.get(0).getEndDate());
        assertEquals(LocalDate.parse("2024-03-20"), timeSlotList.get(1).getStartDate());
        assertEquals(LocalDate.parse("2024-03-27"), timeSlotList.get(1).getEndDate());
    }

    @Test
    public void invalidEditFreeTimeSlotWithReservationOverlaps() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-02-18");
        LocalDate endDate = LocalDate.parse("2024-02-22");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void validEditFreeTimeSlotWithDateOverlaps() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-01-25");
        LocalDate endDate = LocalDate.parse("2024-01-31");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 2L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<TimeSlot> timeSlotList=responseEntity.getBody().getFreeTimeSlots().stream().toList();

        assertEquals(1, responseEntity.getBody().getFreeTimeSlots().size());
        assertEquals(LocalDate.parse("2024-01-25"), timeSlotList.get(0).getStartDate());
        assertEquals(LocalDate.parse("2024-01-31"), timeSlotList.get(0).getEndDate());
    }

    @Test
    public void validEditFreeTimeSlotWithEndDateOverlaps() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-01-16");
        LocalDate endDate = LocalDate.parse("2024-01-31");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 3L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<TimeSlot> timeSlotList=responseEntity.getBody().getFreeTimeSlots().stream().toList();

        assertEquals(1, responseEntity.getBody().getFreeTimeSlots().size());
        assertEquals(LocalDate.parse("2024-01-16"), timeSlotList.get(0).getStartDate());
        assertEquals(LocalDate.parse("2024-01-31"),timeSlotList.get(0).getEndDate());
    }

    @Test
    public void validEditFreeTimeSlotWithStartDateOverlaps() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-01-10");
        LocalDate endDate = LocalDate.parse("2024-01-25");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 4L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<TimeSlot> timeSlotList=responseEntity.getBody().getFreeTimeSlots().stream().toList();

        assertEquals(1, responseEntity.getBody().getFreeTimeSlots().size());
        assertEquals(LocalDate.parse("2024-01-10"), timeSlotList.get(0).getStartDate());
        assertEquals(LocalDate.parse("2024-01-25"), timeSlotList.get(0).getEndDate());
    }

    @Test
    public void validEditFreeTimeSlotWithEqualsDate() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-01-20");
        LocalDate endDate = LocalDate.parse("2024-01-30");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 5L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<TimeSlot> timeSlotList=responseEntity.getBody().getFreeTimeSlots().stream().toList();


        assertEquals(1, responseEntity.getBody().getFreeTimeSlots().size());
        assertEquals(LocalDate.parse("2024-01-20"), timeSlotList.get(0).getStartDate());
        assertEquals(LocalDate.parse("2024-01-30"),timeSlotList.get(0).getEndDate());
    }

    @Test
    public void invalidAccommodationIdForEditFreeTimeSlot() {
        headers.add("Authorization", "Bearer " + tokenHost);

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        LocalDate startDate = LocalDate.parse("2024-02-10");
        LocalDate endDate = LocalDate.parse("2024-02-15");
        timeSlotDTO.setStartDate(startDate);
        timeSlotDTO.setEndDate(endDate);
        HttpEntity<TimeSlotDTO> httpEntity = new HttpEntity<>(timeSlotDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editTimeSlot/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 30L);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }



    // edit price

    @Test
    public void validEditPricelistWithoutDateOverlaps() {
        headers.add("Authorization", "Bearer " + tokenHost);

        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        LocalDate startDate = LocalDate.of(2024,2,20);
        LocalDate endDate = LocalDate.of(2024,2,27);
        TimeSlotDTO timeSlot = new TimeSlotDTO(startDate, endDate);
        pricelistItemDTO.setTimeSlot(timeSlot);
        pricelistItemDTO.setPrice(200);
        HttpEntity<PricelistItemDTO> httpEntity = new HttpEntity<>(pricelistItemDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editPricelist/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 6L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getPriceList().get(0).getPrice(), 100);
        assertEquals(responseEntity.getBody().getPriceList().get(0).getTimeSlot().getStartDate(), LocalDate.of(2024,1,20));
        assertEquals(responseEntity.getBody().getPriceList().get(0).getTimeSlot().getEndDate(), LocalDate.of(2024,1,30));

        assertEquals(responseEntity.getBody().getPriceList().get(1).getPrice(), 200);
        assertEquals(responseEntity.getBody().getPriceList().get(1).getTimeSlot().getStartDate(), LocalDate.of(2024,2,20));
        assertEquals(responseEntity.getBody().getPriceList().get(1).getTimeSlot().getEndDate(), LocalDate.of(2024,2,27));
    }

    @Test
    public void validEditPricelistWithDateOverlaps() {
        headers.add("Authorization", "Bearer " + tokenHost);

        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        LocalDate startDate = LocalDate.of(2024,1,25);
        LocalDate endDate = LocalDate.of(2024,1,31);
        TimeSlotDTO timeSlot = new TimeSlotDTO(startDate, endDate);
        pricelistItemDTO.setTimeSlot(timeSlot);
        pricelistItemDTO.setPrice(200);
        HttpEntity<PricelistItemDTO> httpEntity = new HttpEntity<>(pricelistItemDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editPricelist/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 7L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getPriceList().get(0).getPrice(), 100);
        assertEquals(responseEntity.getBody().getPriceList().get(0).getTimeSlot().getStartDate(), LocalDate.of(2024,1,20));
        assertEquals(responseEntity.getBody().getPriceList().get(0).getTimeSlot().getEndDate(), LocalDate.of(2024,1,25));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getPriceList().get(1).getPrice(), 200);
        assertEquals(responseEntity.getBody().getPriceList().get(1).getTimeSlot().getStartDate(), LocalDate.of(2024,1,25));
        assertEquals(responseEntity.getBody().getPriceList().get(1).getTimeSlot().getEndDate(), LocalDate.of(2024,1,31));

    }

    @Test
    public void validEditPricelistWithEqualsDate() {
        headers.add("Authorization", "Bearer " + tokenHost);

        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        LocalDate startDate = LocalDate.of(2024,1,20);
        LocalDate endDate = LocalDate.of(2024,1,30);
        TimeSlotDTO timeSlot = new TimeSlotDTO(startDate, endDate);
        pricelistItemDTO.setTimeSlot(timeSlot);
        pricelistItemDTO.setPrice(200);
        HttpEntity<PricelistItemDTO> httpEntity = new HttpEntity<>(pricelistItemDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editPricelist/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 8L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getPriceList().get(0).getPrice(), 200);
        assertEquals(responseEntity.getBody().getPriceList().get(0).getTimeSlot().getStartDate(), LocalDate.of(2024,1,20));
        assertEquals(responseEntity.getBody().getPriceList().get(0).getTimeSlot().getEndDate(), LocalDate.of(2024,1,30));
    }


    @Test
    public void invalidAccommodationForEditPricelist() {
        headers.add("Authorization", "Bearer " + tokenHost);

        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        LocalDate startDate = LocalDate.of(2024,1,25);
        LocalDate endDate = LocalDate.of(2024,1,30);
        TimeSlotDTO timeSlot = new TimeSlotDTO(startDate, endDate);
        pricelistItemDTO.setTimeSlot(timeSlot);
        pricelistItemDTO.setPrice(200);
        HttpEntity<PricelistItemDTO> httpEntity = new HttpEntity<>(pricelistItemDTO, headers);
        ResponseEntity<AccommodationDTO> responseEntity = restTemplate.exchange("/api/accommodations/editPricelist/{id}", HttpMethod.PUT, httpEntity, AccommodationDTO.class, 30L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
