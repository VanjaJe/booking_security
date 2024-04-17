package com.booking.BookingApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
//@Table(name="roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    String name;

    public Role() {
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
  
    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
