package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.dto.*;
import com.booking.BookingApp.mapper.AccommodationDTOMapper;
import com.booking.BookingApp.mapper.UserDTOMapper;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodations(
            @RequestParam(value = "begin", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end,
            @RequestParam(value = "guestNumber", defaultValue = "0",required = false) int guestNumber,
            @RequestParam(value = "type", required = false) AccommodationType type,
            @RequestParam(value = "start_price", defaultValue = "0",required = false) double startPrice,
            @RequestParam(value = "end_price", defaultValue = "0",required = false) double endPrice,
            @RequestParam(value = "status", required = false) AccommodationStatus status,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "amenities", required = false) List<String> amenities,
            @RequestParam(value = "hostId", required = false)Integer hostId
    ) {

        Collection<Accommodation>accommodations=accommodationService.findAll(begin,end,guestNumber,type,
                startPrice,endPrice,status,country,city,amenities,hostId);
        Collection<AccommodationDTO> accommodationDTOS = accommodations.stream()
                .map(AccommodationDTOMapper::fromAccommodationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }

        
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(accommodation), HttpStatus.OK);
    }
    @GetMapping("/calculatePrice/{id}")
    public double calculateTotalPriceForAccommodation(
            @PathVariable("id") Long id,
            @RequestParam(value = "guestNumber") int guestNumber,
            @RequestParam(value = "begin")@DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam(value = "end")@DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        return accommodationService.calculatePriceForAccommodation(id, guestNumber, begin, end);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<AccommodationDTO> createAccommodation(@Valid @RequestBody CreateAccommodationDTO accommodation) throws Exception {
        Accommodation newAccommodation=AccommodationDTOMapper.fromCreateDTOtoAccommodation(accommodation);
        Accommodation savedAccommodation = accommodationService.create(newAccommodation);
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(savedAccommodation), HttpStatus.CREATED);
    }

    @PostMapping("/{accommodationId}/upload-picture")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<String> uploadImage(
            @PathVariable("accommodationId") Long accommodationId,
            @RequestParam("images") Collection<MultipartFile> imageFiles) throws IOException {
        System.out.println("vanja");

        for(MultipartFile image: imageFiles) {
            System.out.println("Vanjaaa");
            accommodationService.uploadImage(accommodationId, image);
        }
        return new ResponseEntity<>("Pictures uploaded successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('GUEST') or hasRole('ADMIN')")
    @GetMapping(value = "/{accommodationId}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getImages(@PathVariable("accommodationId") Long accommodationId) throws IOException {
        List<String> images = accommodationService.getImages(accommodationId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "accept/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> accept(@RequestBody AccommodationDTO accommodationDTO, @PathVariable Long id)
            throws Exception {
        Accommodation accommodation = AccommodationDTOMapper.fromDTOtoAccommodation(accommodationDTO);
        Accommodation updatedAccommodation = accommodationService.accept(accommodation);
        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "decline/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> decline(@RequestBody AccommodationDTO accommodationDTO, @PathVariable Long id)
            throws Exception {
        Accommodation accommodation = AccommodationDTOMapper.fromDTOtoAccommodation(accommodationDTO);
        Accommodation updatedAccommodation = accommodationService.decline(accommodation);
        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody @Valid AccommodationDTO accommodationDTO, @PathVariable Long id)
            throws Exception {
        Accommodation accommodation = AccommodationDTOMapper.fromDTOtoAccommodation(accommodationDTO);
        Accommodation updatedAccommodation = accommodationService.update(accommodation);
        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }

    @PutMapping(value = "/editPricelist/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_HOST')")
    public ResponseEntity<AccommodationDTO> editAccommodationPricelistItem(@RequestBody PricelistItemDTO pricelistDTO, @PathVariable Long id)
            throws Exception {
//        Accommodation accommodationForUpdate = accommodationService.findOne(id);
        Accommodation updatedAccommodation = accommodationService.editAccommodationPricelistItem(pricelistDTO,id);

        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }


    @PutMapping("/changeFreeTimeSlots/{accommodationId}")
    public ResponseEntity<AccommodationDTO> changeFreeTimeSlots(
            @PathVariable Long accommodationId,
            @RequestBody TimeSlotDTO reservationTimeSlot) {
         Accommodation accommodation = accommodationService.changeFreeTimeSlotsAcceptingReservation(accommodationId, reservationTimeSlot);
         if(accommodation == null){
             return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
         }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(accommodation), HttpStatus.OK);
    }

    @PutMapping(value = "/editTimeSlot/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<AccommodationDTO> editAccommodationFreeTimeSlots(@RequestBody TimeSlotDTO timeSlotDTO, @PathVariable Long id)
            throws Exception {

        Accommodation updatedAccommodation = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, id);
        System.out.println(updatedAccommodation);
        System.out.println("dosaoooooooooooo");
        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<AccommodationDTO> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<AccommodationDTO>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('HOST')")
    @PutMapping(value = "request/approval", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateRequestApproval(@RequestBody @Valid AccommodationDTO accommodationDTO)
            throws Exception {
        Accommodation accommodation = AccommodationDTOMapper.fromDTOtoAccommodation(accommodationDTO);
        Accommodation updatedAccommodation = accommodationService.updateRequestApproval(accommodation);
        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }
}
