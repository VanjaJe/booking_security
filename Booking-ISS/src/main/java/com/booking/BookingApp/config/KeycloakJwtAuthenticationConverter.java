package com.booking.BookingApp.config;

import com.booking.BookingApp.domain.Guest;
import com.booking.BookingApp.domain.Host;
import com.booking.BookingApp.domain.enums.Privilege;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                        new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractResourceRoles(source).stream()
                ).collect(Collectors.toSet())
        );
    }

    private Collection<? extends GrantedAuthority>extractResourceRoles(Jwt jwt){
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");
        String role="GUEST";

        for (String r : roles) {
            if (r.equals("HOST")) {
                role = r;
                break;
            }
        }
        Collection<Privilege> privileges=new ArrayList<>();
        switch (role){
            case "GUEST":
                privileges=Guest.getPrivileges();
                break;
            case "HOST":
                privileges=Host.getPrivileges();
                break;
        }

        return privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toSet());

//        return roles.stream()
//                .map(privilege -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
//                .collect(Collectors.toSet());


    }
}
