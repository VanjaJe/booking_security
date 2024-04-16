package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "guest_notifications")
@SQLDelete(sql = "UPDATE guest_notification SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class GuestNotification extends Notification{

    @Column(name = "guest_id")
    private Long guestId;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;

    public GuestNotification(Long id, String text, LocalDate date, boolean turnedOn, NotificationType type, Long guestId, boolean deleted) {
//        super(id, text, date, turnedOn, type, deleted);
        this.guestId = guestId;
    }
}
