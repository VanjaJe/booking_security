package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.AccommodationComments;
import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.HostComments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;

import java.util.Collection;


public interface ICommentService {

    Collection<Comments> findAll(Status status);

    Collection<AccommodationComments> findAllAccommodationComments(Status status);

    Collection<HostComments> findAllHostComments(Status status);

    Comments findById(Long id);

    Collection<Comments> findByHostId(Long id, Status status);

    Collection<Comments> findByAccommodationId(Long id, Status status);

    double findHostRating(Long id);

    double findAccommodationRating(Long id);

    Comments createHostComment(Comments comment, Long id);

    Comments createAccommodationComment(Comments comment, Long id);

    Comments update(Comments commentForUpdate);

    Comments approve(Comments commentForApproving);

    Comments decline(Comments commentForDeclining);

    void delete(Long id);

    Comments reportComment(Comments commentForUpdate, Status status);
}