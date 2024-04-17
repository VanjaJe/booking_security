package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.PricelistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PriceListItemRepository extends JpaRepository<PricelistItem, Long> {
}
