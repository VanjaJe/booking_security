package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "guests")
public class Guest extends User {

    @ManyToMany
    @JoinTable(name = "favorite_accommodation",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id"))
    private Collection<Accommodation> favoriteAccommodations;

    public Guest(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account, boolean deleted, Collection<Accommodation> favoriteAccommodations) {
        super(id, firstName, lastName, address, phoneNumber, account, deleted);
        this.favoriteAccommodations = favoriteAccommodations;
    }


}
