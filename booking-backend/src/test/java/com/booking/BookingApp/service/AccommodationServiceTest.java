package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.PricelistItem;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.PricelistItemDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.AccommodationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccommodationServiceTest {

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    AvailabilityService availabilityService;

    @InjectMocks
    AccommodationService accommodationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_when_reservation_overlaps() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-03-23"), LocalDate.parse("2024-02-25"));

        Collection<TimeSlot> accommodationFreeTimeslots = getAccommodationFreeTimeslots();

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setFreeTimeSlots(accommodationFreeTimeslots);
        accommodation.setName("Accommodation1");

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        when(availabilityService.reservationOverlaps(timeSlotDTO, 1L)).thenReturn(true);

        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 1L);

        verify(availabilityService).reservationOverlaps(timeSlotDTO, 1L);
        verifyNoMoreInteractions(availabilityService);
        assertNull(result);
    }

    @Test
    public void test_when_accommodation_id_not_valid() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));

        when(accommodationRepository.findById(3L)).thenReturn(Optional.empty());

        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 3L);

        verifyNoInteractions(availabilityService);
        assertNull(result);
    }

    @Test
    public void test_when_reservation_not_overlaps() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));

        Collection<TimeSlot> accommodationFreeTimeslots = getAccommodationFreeTimeslots();

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setFreeTimeSlots(accommodationFreeTimeslots);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        when(availabilityService.reservationOverlaps(timeSlotDTO, 1L)).thenReturn(false);

        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 1L);

        verify(availabilityService).reservationOverlaps(timeSlotDTO, 1L);
        verify(availabilityService).updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);
        assertNotNull(result);
        assertEquals(accommodation.getId(), result.getId());
    }

    public Collection<TimeSlot> getAccommodationFreeTimeslots() {
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);
        return  accommodationFreeTimeslots;
    }

    //for edit price

    @Test
    public void test_edit_price_with_invalid_accommodation_id() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        when(accommodationRepository.findById(3L)).thenReturn(Optional.empty());

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 3L);

        verify(accommodationRepository).findById(3L);
        verifyNoMoreInteractions(accommodationRepository);
        assertNull(result);
    }

    @Test
    public void test_edit_price_without_overlaps() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();
        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-01-25"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-01-29"));
    }


    @Test
    public void test_edit_price_with_overlaps_new_price_is_between() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-13"), LocalDate.parse("2024-02-17"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();
        assertEquals(arrayList.get(0).getPrice(), 800);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-10"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-13"));

        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-13"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-17"));

        assertEquals(arrayList.get(2).getPrice(), 800);
        assertEquals(arrayList.get(2).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-17"));
        assertEquals(arrayList.get(2).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-20"));
    }


    @Test
    public void test_edit_price_with_overlaps_start() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-15"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 800);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-15"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-20"));

        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-03"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-15"));
    }


    @Test
    public void test_edit_price_with_overlaps_end() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-13"), LocalDate.parse("2024-02-25"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 800);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-10"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-13"));

        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-13"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-25"));
    }


    @Test
    public void test_edit_price_with_overlaps() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-25"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 1000);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-03"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-25"));
    }

    @Test
    public void test_edit_price_with_overlaps_and_equals_date() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 1000);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-10"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-20"));
    }
}