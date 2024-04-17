package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;
import com.booking.BookingApp.dto.CommentsDTO;
import com.booking.BookingApp.mapper.AmenityDTOMapper;
import com.booking.BookingApp.mapper.CommentsDTOMapper;
import com.booking.BookingApp.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<AmenityDTO>> getAmenities() {
        Collection<Amenity> amenities = amenityService.findAll();

        Collection<AmenityDTO> amenitiesDTO = amenities.stream()
                .map(AmenityDTOMapper::fromAmenitytoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<AmenityDTO>>(amenitiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<AmenityDTO> getById(@PathVariable("id") Long id) {
        Amenity amenity = amenityService.findById(id);
        return new ResponseEntity<AmenityDTO>(new AmenityDTO(amenity), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> createAmenity(@RequestBody AmenityDTO amenityDTO) {
        Amenity amenityModel = AmenityDTOMapper.fromDTOtoAmenity(amenityDTO);
        Amenity savedAmenity = amenityService.create(amenityModel);
        return new ResponseEntity<AmenityDTO>(new AmenityDTO(savedAmenity), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> updateAmenity(@RequestBody AmenityDTO amenityDTO, @PathVariable("id") Long id) {
        Amenity amenityForUpdate = amenityService.findById(id);
        Amenity amenity = AmenityDTOMapper.fromDTOtoAmenity(amenityDTO);
        Amenity updatedAmenity = amenityService.update(amenityForUpdate, amenity);
        return new ResponseEntity<AmenityDTO>(new AmenityDTO(updatedAmenity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AmenityDTO> deleteAmenity(@PathVariable("id") Long id) {
        amenityService.delete(id);
        return new ResponseEntity<AmenityDTO>(HttpStatus.NO_CONTENT);
    }
}