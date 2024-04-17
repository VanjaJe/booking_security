package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "timeslots")
@SQLDelete(sql = "UPDATE timeslots SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name="deleted")
    private  boolean deleted = Boolean.FALSE;
    public TimeSlot(Long id, LocalDate startDate, LocalDate endDate, boolean deleted) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = deleted;
    }

    public TimeSlot(LocalDate startDate, LocalDate endDate, boolean deleted) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = deleted;
    }

    public TimeSlot(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
