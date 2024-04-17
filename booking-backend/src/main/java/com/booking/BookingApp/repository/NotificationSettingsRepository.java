package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.NotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {

    NotificationSettings findByUser_Id(Long id);

}
