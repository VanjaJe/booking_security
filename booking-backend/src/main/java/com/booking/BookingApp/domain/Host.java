package com.booking.BookingApp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hosts")
//@SQLDelete(sql = "UPDATE hosts SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
public class Host extends User {

    public Host(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account,boolean deleted) {
        super(id, firstName, lastName, address, phoneNumber, account,deleted);
    }
}
