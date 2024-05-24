//package com.booking.BookingApp.controller;
//
//import com.booking.BookingApp.domain.Accommodation;
//import com.booking.BookingApp.domain.FavoriteAccommodation;
//import com.booking.BookingApp.dto.AccommodationDTO;
//import com.booking.BookingApp.dto.FavoriteAccommodationDTO;
//import com.booking.BookingApp.mapper.AccommodationDTOMapper;
//import com.booking.BookingApp.mapper.FavoriteAccommodationDTOMapper;
//import com.booking.BookingApp.service.interfaces.IFavoriteAccommodationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/api/favoriteAccommodations")
//public class FavoriteAccommodationController {
//    @Autowired
//    IFavoriteAccommodationService favoriteAccommodationService;
//
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('GUEST')")
//    public ResponseEntity<FavoriteAccommodationDTO> getFavoriteAccommodation(@PathVariable("id") Long id) {
//        FavoriteAccommodation accommodation = favoriteAccommodationService.findOne(id);
//        if (accommodation == null) {
//            return new ResponseEntity<FavoriteAccommodationDTO>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<FavoriteAccommodationDTO>(FavoriteAccommodationDTOMapper.fromFavoritetoDTO(accommodation), HttpStatus.OK);
//    }
//    //get favorite accommodations for a guest
//    @GetMapping(value = "/guest/{guestId}",produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('GUEST')")
//    public ResponseEntity<Collection<FavoriteAccommodationDTO>> getGuestFavoriteAccommodation(@PathVariable("guestId") Long id) {
//        Collection<FavoriteAccommodation>accommodations=favoriteAccommodationService.findAllForGuest(id);
//        Collection<FavoriteAccommodationDTO> accommodationDTOS = accommodations.stream()
//                .map(FavoriteAccommodationDTOMapper::fromFavoritetoDTO)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(accommodationDTOS, HttpStatus.OK);
//    }
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('GUEST')")
//    public ResponseEntity<FavoriteAccommodationDTO> addFavoriteAccommodation(@RequestBody FavoriteAccommodationDTO accommodation) throws Exception {
//        FavoriteAccommodation savedAccommodation = favoriteAccommodationService.create(FavoriteAccommodationDTOMapper.fromDTOtoFavorite(accommodation));
//        return new ResponseEntity<FavoriteAccommodationDTO>(FavoriteAccommodationDTOMapper.fromFavoritetoDTO(savedAccommodation), HttpStatus.CREATED);
//    }
//    @DeleteMapping(value = "/{id}")
//    @PreAuthorize("hasRole('HOST')")
//    public ResponseEntity<FavoriteAccommodationDTO> removeFavoriteAccommodation(@PathVariable("id") Long id) {
//        favoriteAccommodationService.delete(id);
//        return new ResponseEntity<FavoriteAccommodationDTO>(HttpStatus.NO_CONTENT);
//    }
//}
