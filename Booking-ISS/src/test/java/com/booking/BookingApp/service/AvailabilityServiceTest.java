package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AvailabilityServiceTest {

    @Mock
    RequestRepository requestRepository;

    @InjectMocks
    AvailabilityService availabilityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void test_when_reservation_not_overlaps_free_dates_are_between() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-12"), LocalDate.parse("2024-02-18"));

        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L, LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);


        Collection<TimeSlot> result = availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);

        assertNotNull(result);
        List<TimeSlot> listTimeSlots = result.stream().toList();
        assertEquals(listTimeSlots.get(0).getStartDate(),LocalDate.parse("2024-02-12"));
        assertEquals(listTimeSlots.get(0).getEndDate(),LocalDate.parse("2024-02-18"));
    }


    @Test
    public void test_when_reservation_not_overlaps_free_dates_overlaps_on_start() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-08"), LocalDate.parse("2024-02-15"));

        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L, LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);


        Collection<TimeSlot> result = availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);

        assertNotNull(result);
        List<TimeSlot> listTimeSlots = result.stream().toList();
        assertEquals(listTimeSlots.get(0).getStartDate(),LocalDate.parse("2024-02-08"));
        assertEquals(listTimeSlots.get(0).getEndDate(),LocalDate.parse("2024-02-15"));
    }


    @Test
    public void test_when_reservation_not_overlaps_free_dates_overlaps_on_end() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-18"), LocalDate.parse("2024-02-25"));

        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L, LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);


        Collection<TimeSlot> result = availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);

        assertNotNull(result);
        List<TimeSlot> listTimeSlots = result.stream().toList();
        assertEquals(listTimeSlots.get(0).getStartDate(),LocalDate.parse("2024-02-18"));
        assertEquals(listTimeSlots.get(0).getEndDate(),LocalDate.parse("2024-02-25"));
    }


    @Test
    public void test_when_reservation_not_overlaps_free_dates_overlaps() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-05"), LocalDate.parse("2024-02-25"));

        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L, LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);


        Collection<TimeSlot> result = availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);

        assertNotNull(result);
        List<TimeSlot> listTimeSlots = result.stream().toList();
        assertEquals(listTimeSlots.get(0).getStartDate(),LocalDate.parse("2024-02-05"));
        assertEquals(listTimeSlots.get(0).getEndDate(),LocalDate.parse("2024-02-25"));
    }

    @Test
    public void test_when_reservation_not_overlaps_free_dates_are_equals() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"));

        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L, LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);


        Collection<TimeSlot> result = availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);

        assertNotNull(result);
        List<TimeSlot> listTimeSlots = result.stream().toList();
        assertEquals(listTimeSlots.get(0).getStartDate(),LocalDate.parse("2024-02-10"));
        assertEquals(listTimeSlots.get(0).getEndDate(),LocalDate.parse("2024-02-20"));
    }

    @Test
    public void test_when_reservation_not_overlaps_free_dates_not_overlaps() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-03-10"), LocalDate.parse("2024-03-20"));

        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L, LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);


        Collection<TimeSlot> result = availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);

        assertNotNull(result);
        List<TimeSlot> listTimeSlots = result.stream().toList();
        assertEquals(listTimeSlots.get(0).getStartDate(),LocalDate.parse("2024-02-10"));
        assertEquals(listTimeSlots.get(0).getEndDate(),LocalDate.parse("2024-02-20"));

        assertEquals(listTimeSlots.get(1).getStartDate(),LocalDate.parse("2024-03-10"));
        assertEquals(listTimeSlots.get(1).getEndDate(),LocalDate.parse("2024-03-20"));
    }

//
//
//    @Test
//    public void test_when_reservation_not_overlaps_and_start_free_date_overlaps() throws Exception {
//        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-05"), LocalDate.parse("2024-02-15"));
//
//        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L,LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
//        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
//        accommodationFreeTimeslots.add(accommodationTimeSlot1);
//
//        Accommodation accommodation = new Accommodation();
//        accommodation.setId(1L);
//        accommodation.setFreeTimeSlots(accommodationFreeTimeslots);
//
//        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
//
//        accommodation.getFreeTimeSlots().remove(accommodationTimeSlot1);
//        TimeSlot returnedTimeSlot = new TimeSlot(1L, timeSlotDTO.getStartDate(), timeSlotDTO.getEndDate(), false);
//        accommodation.getFreeTimeSlots().add(returnedTimeSlot);
//
//        when(availabilityService.reservationOverlaps(timeSlotDTO, 1L)).thenReturn(false);
////        when(availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots)).thenReturn(returnedTimeSlot);
//        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);
//
//        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 1L);
//        System.out.println(result);
//
//        verify(availabilityService).reservationOverlaps(timeSlotDTO, 1L);
//        verify(availabilityService).updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);
//        assertNotNull(result);
//        assertEquals(result.getFreeTimeSlots(), accommodation.getFreeTimeSlots());
//    }
//
//
//    @Test
//    public void test_when_reservation_not_overlaps_and_end_free_date_overlaps() throws Exception {
//        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-15"), LocalDate.parse("2024-02-25"));
//
//        TimeSlot accommodationTimeSlot1 = new TimeSlot(3L,LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
//        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
//        accommodationFreeTimeslots.add(accommodationTimeSlot1);
//
//        Accommodation accommodation = new Accommodation();
//        accommodation.setId(1L);
//        accommodation.setFreeTimeSlots(accommodationFreeTimeslots);
//
//        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
//
//        accommodation.getFreeTimeSlots().remove(accommodationTimeSlot1);
//        TimeSlot returnedTimeSlot = new TimeSlot(1L, timeSlotDTO.getStartDate(), timeSlotDTO.getEndDate(), false);
//        accommodation.getFreeTimeSlots().add(returnedTimeSlot);
//
//        when(availabilityService.reservationOverlaps(timeSlotDTO, 1L)).thenReturn(false);
////        when(availabilityService.updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots)).thenReturn(returnedTimeSlot);
//        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);
//
//        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 1L);
//        System.out.println(result);
//
//        verify(availabilityService).reservationOverlaps(timeSlotDTO, 1L);
//        verify(availabilityService).updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);
//        assertNotNull(result);
//        assertEquals(result.getFreeTimeSlots(), accommodation.getFreeTimeSlots());
//    }
//
//    public Collection<TimeSlot> getAccommodationFreeTimeslots() {
//        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
//        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
//        accommodationFreeTimeslots.add(accommodationTimeSlot1);
//        return  accommodationFreeTimeslots;
//    }
}
