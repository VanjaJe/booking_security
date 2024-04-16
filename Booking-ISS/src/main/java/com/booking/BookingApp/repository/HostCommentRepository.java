package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.AccommodationComments;
import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.HostComments;
import com.booking.BookingApp.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface HostCommentRepository extends JpaRepository<HostComments,Long> {

    Collection<HostComments> findAllByStatus(Status status);

    Collection<Comments> findByHost_Id(Long id);
    Collection<Comments> findByHost_IdAndStatus(Long id, Status status);

}
