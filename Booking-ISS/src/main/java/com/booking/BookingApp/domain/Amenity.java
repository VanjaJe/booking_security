package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.swing.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "amenities")
@SQLDelete(sql = "UPDATE amenities SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="amenity_name")
    private String name;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;
//    private ImageIcon icon;

    public Amenity(Long id, String name, boolean deleted) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
//        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ",deleted=" + deleted +
                '}';
    }
}
