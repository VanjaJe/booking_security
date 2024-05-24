package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Privilege;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public static Collection<Privilege> getPrivileges(){
        Collection<Privilege> privileges=new ArrayList<>();
        privileges.add(Privilege.READ_ACCOMMODATION);
        privileges.add(Privilege.DELETE_ACCOMMODATION);
        privileges.add(Privilege.WRITE_ACCOMMODATION);
        privileges.add(Privilege.UPDATE_ACCOMMODATION);
        privileges.add(Privilege.READ_HOST_REQUEST);
        privileges.add(Privilege.MANAGE_HOST_REQUESTS);
        privileges.add(Privilege.READ_COMMENT);
        privileges.add(Privilege.REPORT_COMMENT);
        privileges.add(Privilege.MANAGE_COMMENT);
        privileges.add(Privilege.BLOCK_USER);
        privileges.add(Privilege.READ_NOTIFICATIONS);
        privileges.add(Privilege.READ_HOST_SETTINGS);
        privileges.add(Privilege.WRITE_NOTIFICATION);
        privileges.add(Privilege.UPDATE_HOST_SETTINGS);
        privileges.add(Privilege.UPDATE_NOTIFICATIONS);
        privileges.add(Privilege.VIEW_REPORT);

        return privileges;
    }
}
