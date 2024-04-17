package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.GuestNotificationSettings;
import com.booking.BookingApp.domain.HostNotificationSettings;
import com.booking.BookingApp.domain.NotificationSettings;
import com.booking.BookingApp.domain.User;

public interface INotificationSettingsService {

    NotificationSettings createNotificationSettings(User user);

    HostNotificationSettings getHostSettings(User user);
    GuestNotificationSettings getGuestSettings(User user);
}
