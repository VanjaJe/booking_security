package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findByName(String name);
}
