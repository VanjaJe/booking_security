package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository  extends JpaRepository<Host,Long> {

}
