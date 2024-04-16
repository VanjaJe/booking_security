package com.booking.BookingApp.dto;

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
public class CommentsDTO {
    private Long id;
    private String text;
    private LocalDate date;
    private double rating;
    private Status status;
    private Guest guest;
//    private Host host;
//    private AccommodationDTO accommodationDTO;

    public CommentsDTO(Long id, String text, LocalDate date, double rating, Status status, Guest guest) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
    }

//    public CommentsDTO(Long id, String text, LocalDate date, double rating, Status status, Guest guest, Host host) {
//        this.id = id;
//        this.text = text;
//        this.date = date;
//        this.rating = rating;
//        this.status = status;
//        this.guest = guest;
//        this.host = host;
//    }
//
//    public CommentsDTO(Long id, String text, LocalDate date, double rating, Status status, Guest guest, AccommodationDTO accommodationDTO) {
//        this.id = id;
//        this.text = text;
//        this.date = date;
//        this.rating = rating;
//        this.status = status;
//        this.guest = guest;
//        this.accommodationDTO = accommodationDTO;
//    }

    public CommentsDTO(Comments comments) {
        this(comments.getId(), comments.getText(), comments.getDate(), comments.getRating(), comments.getStatus(),
                comments.getGuest());
    }

    public CommentsDTO(String text, LocalDate date, double rating, Status status, Guest guest) {
    }

    @Override
    public String toString() {
        return "CommentsDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", rating=" + rating +
                ", status=" + status +
                ", guest=" + guest +
                '}';
    }
}
