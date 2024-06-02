package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.LdapUser;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class UserAttributesMapper implements AttributesMapper<LdapUser> {

    @Override
    public LdapUser mapFromAttributes(Attributes attrs) throws NamingException {
        LdapUser ldapUser = new LdapUser();

        if (attrs.get("mail") != null) {
            ldapUser.setEmail((String) attrs.get("mail").get());
            ldapUser.setCn((String) attrs.get("cn").get());
            ldapUser.setSn((String) attrs.get("sn").get());
            ldapUser.setUid((String) attrs.get("uid").get());
        }
        return ldapUser;
    }
}
