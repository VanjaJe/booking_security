package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.Guest;
import com.booking.BookingApp.domain.Host;
import com.booking.BookingApp.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationCommentDTO extends CommentsDTO{
    private String text;
    private LocalDate date;
    private double rating;
    private Status status;
    private Guest guest;
    private Accommodation accommodation;

    public AccommodationCommentDTO(String text, LocalDate date, double rating, Status status, Guest guest, Accommodation accommodation) {
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
        this.accommodation = accommodation;
    }
}
