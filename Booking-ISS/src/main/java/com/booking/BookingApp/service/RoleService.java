package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Role;
import com.booking.BookingApp.repository.RoleRepository;
import com.booking.BookingApp.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
