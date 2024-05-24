package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findGuestById(Long id);
    Guest findGuestByAccount_Username(String username);
}
