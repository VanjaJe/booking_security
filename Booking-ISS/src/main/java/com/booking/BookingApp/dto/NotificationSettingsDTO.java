package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSettingsDTO {
    private NotificationType type;
    private boolean turnedOn;

    public NotificationSettingsDTO(NotificationType type,boolean turnedOn) {
        this.type = type;
        this.turnedOn=turnedOn;
    }
}
