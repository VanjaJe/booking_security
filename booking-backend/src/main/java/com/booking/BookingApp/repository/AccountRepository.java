package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Collection<Account> findAllBy();
    public Optional<Account> findById(Long id);
    public Account findByUsername(String username);
}
