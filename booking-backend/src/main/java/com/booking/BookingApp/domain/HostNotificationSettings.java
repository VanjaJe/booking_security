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
@Table(name = "host_notification_settings")
public class HostNotificationSettings extends NotificationSettings{

    private boolean isRequestCreated = Boolean.FALSE;
    private boolean isReservationCancelled = Boolean.FALSE;
    private boolean isRated = Boolean.FALSE;
    private boolean isAccommodationRated = Boolean.FALSE;

    public HostNotificationSettings(Long id, User user, boolean isRequestCreated, boolean isReservationCancelled, boolean isRated, boolean isAccommodationRated) {
        super(id, user);
        this.isRequestCreated = isRequestCreated;
        this.isReservationCancelled = isReservationCancelled;
        this.isRated = isRated;
        this.isAccommodationRated = isAccommodationRated;
    }
}
