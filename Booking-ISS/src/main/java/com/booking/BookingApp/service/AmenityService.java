package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;
import com.booking.BookingApp.repository.AmenityRepository;
import com.booking.BookingApp.service.interfaces.IAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

@Service

public class AmenityService implements IAmenityService {

    @Autowired
    AmenityRepository amenityRepository;

    @Override
    public Collection<Amenity> findAll() {return amenityRepository.findAll();}

    @Override
    public Amenity findById(Long id) {
        return new Amenity(1L, "Swimming Pool",false);
    }

    @Override
    public Amenity create(Amenity amenity) {return new Amenity(1L, "Swimming Pool",false);}

    @Override
    public Amenity update(Amenity amenityForUpdate, Amenity amenity) {return new Amenity(1L, "Swimming Pool",false);}

    @Override
    public void delete(Long id) {}


}
