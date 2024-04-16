package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notifications")
@SQLDelete(sql = "UPDATE notifications SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "notification_text")
    private String text;

    @Column(name = "date")
    private String date;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;

    @Column(name="read")
    private boolean read = Boolean.FALSE;

    public Notification(Long id, String text, String date, NotificationType type, boolean deleted) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.type = type;
        this.deleted=deleted;
    }
    public Notification(Long id, String text, String date, NotificationType type, boolean deleted,boolean read) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.type = type;
        this.deleted=deleted;
        this.read=read;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", type=" + type +
                ",deleted=" + deleted +
                '}';
    }
}