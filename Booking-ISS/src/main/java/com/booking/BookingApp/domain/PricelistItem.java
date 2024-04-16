package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pricelist_items")
@SQLDelete(sql = "UPDATE pricelist_items SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class PricelistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    private TimeSlot timeSlot;

    @Column(name = "price")
    private double price;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;
    public PricelistItem(Long id, TimeSlot timeSlot, double price,boolean deleted) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.deleted = deleted;
    }

    public PricelistItem(TimeSlot timeSlot, double price,boolean deleted) {
        this.timeSlot = timeSlot;
        this.price = price;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "PricelistItem{" +
                "timeSlot=" + timeSlot +
                ", price=" + price +
                ", deleted=" + deleted +
                '}';
    }
}
