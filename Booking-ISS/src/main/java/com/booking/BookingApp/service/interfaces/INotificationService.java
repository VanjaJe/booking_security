package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.GuestNotificationSettings;
import com.booking.BookingApp.domain.HostNotificationSettings;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationSettingsDTO;

import java.text.ParseException;
import java.util.Collection;

public interface INotificationService {
    void delete(Long id);

    Notification createUserNotification(Notification notification) throws Exception;

    Collection<Notification> findAllForUser(String id);

    GuestNotificationSettings updateGuestSettings(String id, GuestNotificationSettings settings);

    HostNotificationSettings updateHostSettings(String id, HostNotificationSettings settings);

    Notification updateNotification(Long id, Notification notification);

    Notification findNewForUser(String id)throws ParseException;

//    Collection<Notification> updateSettings(Long id, NotificationType type);
}
