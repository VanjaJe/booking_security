package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Account;
import com.booking.BookingApp.domain.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
