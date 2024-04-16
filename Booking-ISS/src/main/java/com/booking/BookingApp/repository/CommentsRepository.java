package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Collection<Comments> findByGuest_Id(Long id);

    @Modifying
    @Query(value = "UPDATE comments SET deleted = true WHERE guest_id=:id", nativeQuery = true)
    void deleteByGuestId(Long id);
}
