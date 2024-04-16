package com.booking.BookingApp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "guest_notification_settings")
public class GuestNotificationSettings extends NotificationSettings{

    private boolean isRequestResponded = Boolean.FALSE;

    public GuestNotificationSettings(Long id, User user, boolean isRequestResponded) {
        super(id, user);
        this.isRequestResponded = isRequestResponded;
    }
}
