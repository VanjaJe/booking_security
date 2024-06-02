package com.booking.BookingApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entry(objectClasses = {"inetOrgPerson"})
public class LdapUser {
    @Id
    private String uid;

    @Attribute(name = "cn")
    private String cn;

    @Attribute(name = "sn")
    private String sn;

    @Attribute(name = "mail")
    private String email;


    public LdapUser(User user){
        setUid(user.getAccount().getUsername());
        setCn(user.getFirstName());
        setSn(user.getLastName());
        setEmail(user.getAccount().getUsername());
    }
}
