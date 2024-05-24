package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Privilege;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public static Collection<Privilege> getPrivileges(){
        List<Privilege>privileges=new ArrayList<>();
        privileges.add(Privilege.READ_ACCOMMODATION);
        privileges.add(Privilege.ADD_FAVORTIES);
        privileges.add(Privilege.READ_FAVORITES);
        privileges.add(Privilege.UPDATE_FAVORITES);
        privileges.add(Privilege.WRITE_REQUEST);
        privileges.add(Privilege.READ_GUEST_REQUEST);
        privileges.add(Privilege.MANAGE_GUEST_REQUESTS);
        privileges.add(Privilege.READ_COMMENT);
        privileges.add(Privilege.WRITE_COMMENT);
        privileges.add(Privilege.DELETE_COMMENT);
        privileges.add(Privilege.READ_NOTIFICATIONS);
        privileges.add(Privilege.READ_GUEST_SETTINGS);
        privileges.add(Privilege.WRITE_NOTIFICATION);
        privileges.add(Privilege.UPDATE_GUEST_SETTINGS);
        privileges.add(Privilege.UPDATE_NOTIFICATIONS);
        return privileges;
    }
}