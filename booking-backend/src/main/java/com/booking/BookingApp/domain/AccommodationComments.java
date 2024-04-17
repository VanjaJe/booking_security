package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accommodation_comments")
public class AccommodationComments extends Comments{

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private Accommodation accommodation;

    public AccommodationComments(Long id, String text, LocalDate date, double rating, Status status, Guest guest, Accommodation accommodationId,boolean deleted) {
        super(id, text, date, rating, status, guest,deleted);
        this.accommodation = accommodationId;
    }
}
