package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
@SQLDelete(sql = "UPDATE requests SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    private TimeSlot timeSlot;

    @Column(name = "price")
    private double price;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = {CascadeType.REMOVE})
    private Guest guest;
  
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = {CascadeType.REMOVE})
    private Accommodation accommodation;

    @Column(name = "guest_number")
    private int guestNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus status;


    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;

    public Request(Long id, TimeSlot timeSlot, double price, Guest guest, Accommodation accommodation, int guestNumber, RequestStatus status, Boolean deleted) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.guest = guest;
        this.accommodation = accommodation;
        this.guestNumber = guestNumber;
        this.status = status;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", timeSlot=" + timeSlot +
                ", price=" + price +
                ", guest=" + guest +
                ", accommodation=" + accommodation +
                ", guestNumber=" + guestNumber +
                ", status=" + status +
                ",deleted=" + deleted +
                '}';
    }
}

