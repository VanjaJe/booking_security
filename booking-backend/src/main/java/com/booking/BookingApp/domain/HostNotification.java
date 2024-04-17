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
@Table(name = "host_notifications")
@SQLDelete(sql = "UPDATE host_notifications SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class HostNotification extends Notification{

    @Column(name = "host_id")
    private Long hostId;

    @Column(name="deleted")
    private boolean deleted;

    public HostNotification(Long id, String text, LocalDate date, boolean turnedOn, NotificationType type, Long hostId, boolean deleted) {
//        super(id, text, date, turnedOn, type, deleted);
        this.hostId = hostId;
    }
}
