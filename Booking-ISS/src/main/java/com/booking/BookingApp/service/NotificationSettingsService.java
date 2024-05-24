package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.GuestNotificationSettings;
import com.booking.BookingApp.domain.HostNotificationSettings;
import com.booking.BookingApp.domain.NotificationSettings;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.repository.GuestNotificationSettingsRepository;
import com.booking.BookingApp.repository.HostNotificationSettingsRepository;
import com.booking.BookingApp.service.interfaces.INotificationSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationSettingsService implements INotificationSettingsService {

    @Autowired
    HostNotificationSettingsRepository hostNotificationSettingsRepository;

    @Autowired
    GuestNotificationSettingsRepository guestNotificationSettingsRepository;

    @Override
    public NotificationSettings createNotificationSettings(User user) {
        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
            GuestNotificationSettings guestSettings = new GuestNotificationSettings();
            guestSettings.setRequestResponded(true);
            guestSettings.setUser(user);

            guestNotificationSettingsRepository.save(guestSettings);

            return  guestSettings;
        }
        else {
            HostNotificationSettings hostSettings = new HostNotificationSettings();
            hostSettings.setRequestCreated(true);
            hostSettings.setReservationCancelled(true);
            hostSettings.setRated(true);
            hostSettings.setAccommodationRated(true);
            hostSettings.setUser(user);

            hostNotificationSettingsRepository.save(hostSettings);

            return hostSettings;
        }
    }

    public HostNotificationSettings getHostSettings(User user) {
        return hostNotificationSettingsRepository.findByUser_Account_Username(user.getAccount().getUsername());
    }

    public GuestNotificationSettings getGuestSettings(User user) {
        return guestNotificationSettingsRepository.findByUser_Account_Username(user.getAccount().getUsername());
    }
}
