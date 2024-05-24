package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.GuestNotificationSettings;
import com.booking.BookingApp.domain.NotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface GuestNotificationSettingsRepository extends JpaRepository<GuestNotificationSettings, Long> {
    GuestNotificationSettings findByUser_Account_Username(String id);
//
//    @Query("SELECT n FROM GuestNotification n " +
//            "LEFT JOIN GuestNotificationSettings gns " +
//            "WHERE n.guestId = gns.user.id")
//    GuestNotificationSettings findByUser_id(Long id);
//

}
