package com.booking.BookingApp.dto;

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
public class CreateHostCommentDTO extends CommentsDTO{
    private String text;
    private LocalDate date;
    private double rating;
    private Status status;
    private Guest guest;
    private Host host;

    public CreateHostCommentDTO(String text, LocalDate date, double rating, Status status, Guest guest, Host host) {
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
        this.host = host;
    }
}
