package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.HostNotificationSettings;
import com.booking.BookingApp.domain.NotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostNotificationSettingsRepository extends JpaRepository<HostNotificationSettings, Long> {
    HostNotificationSettings findByUser_Id(Long id);

}
