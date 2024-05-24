package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Collection<Notification> findAllByUser_Account_UsernameAndReadIsFalse(String id);
}
