package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.repository.AccommodationCommentRepository;
import com.booking.BookingApp.repository.CommentsRepository;
import com.booking.BookingApp.repository.HostCommentRepository;
import com.booking.BookingApp.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    AccommodationCommentRepository accommodationCommentRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    UserService userService;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    RequestService requestService;

    @Autowired
    HostCommentRepository hostCommentRepository;

    @Override
    public Collection<Comments> findAll(Status status) {
        return commentsRepository.findAll();
    }

    @Override
    public Collection<AccommodationComments> findAllAccommodationComments(Status status) {
        return accommodationCommentRepository.findAllByStatus(status);
    }

    @Override
    public Collection<HostComments> findAllHostComments(Status status) {
        return hostCommentRepository.findAllByStatus(status);
    }

    @Override
    public Comments findById(Long id) {
        return commentsRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Comments> findByHostId(String id, Status status) {
        return hostCommentRepository.findByHost_Account_UsernameAndStatus(id, Status.ACTIVE);
    }

    @Override
    public Collection<Comments> findByAccommodationId(Long id, Status status) {
        return accommodationCommentRepository.findAllByAccommodation_IdAndStatus(id,status);
    }

    @Override
    public double findAccommodationRating(Long id) {
        Collection<Comments> commentsAndRatings= findByAccommodationId(id,Status.ACTIVE);
        for (Comments com : commentsAndRatings) {
            System.out.println(com.getText());
        }
        double sum=0;
        for (Comments comment:commentsAndRatings){
            sum+=comment.getRating();
        }
        if(sum!=0){
            return (double) (sum/commentsAndRatings.size());
        }
        return 0;
    }

    @Override
    public double findHostRating(String id) {
        Collection<Comments> commentsAndRatings = findByHostId(id,Status.ACTIVE);

        double sum=0;
        for (Comments comment:commentsAndRatings){
            sum+=comment.getRating();
        }
        if(sum!=0){
            return (double) (sum/commentsAndRatings.size());
        }
        return 0;
    }

    @Override
    public Comments createHostComment(Comments comment, String id) {
        Collection<Request> hostRequests = requestService.findByHost(id);

        boolean check=false;
        for (Request request : hostRequests) {
            if (request.getGuest().getId().equals(comment.getGuest().getId())) {
                check=true;
                break;
            }
        }
        if (!check) {
            return null;
        }

        Host host = (Host) userService.findOne(id);
        HostComments hostComment = new HostComments();
        hostComment.setHost(host);
        hostComment.setDate(comment.getDate());
        hostComment.setRating(comment.getRating());
        hostComment.setText(comment.getText());
        hostComment.setGuest(comment.getGuest());
        hostComment.setStatus(Status.PENDING);
        return commentsRepository.save(hostComment);
    }

    @Override
    public Comments createAccommodationComment(Comments comment, Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        Collection<Request> accommodationRequests = requestService.findByAccommodationId(id);

        LocalDate now = LocalDate.now();

        boolean check = false;
        for (Request request : accommodationRequests) {
            if (request.getGuest().getId().equals(comment.getGuest().getId())) {
                if (!now.minusDays(7).isAfter(request.getTimeSlot().getEndDate())) {
                    check=true;
                    break;
                }
            }
        }

        if (!check) {
            return null;
        }

        AccommodationComments accommodationComment = new AccommodationComments();
        accommodationComment.setDate(comment.getDate());
        accommodationComment.setAccommodation(accommodation);
        accommodationComment.setRating(comment.getRating());
        accommodationComment.setDate(comment.getDate());
        accommodationComment.setText(comment.getText());
        accommodationComment.setGuest(comment.getGuest());
        accommodationComment.setStatus(Status.PENDING);
        return commentsRepository.save(accommodationComment);
    }

    @Override
    public Comments update(Comments commentForUpdate) {
        return null;
    }

    @Override
    public Comments approve(Comments commentForApproving) {
        AccommodationComments accommodationComments = accommodationCommentRepository.findById(commentForApproving.getId()).orElse(null);
        if(accommodationComments == null){
            HostComments hostComments = hostCommentRepository.findById(commentForApproving.getId()).orElse(null);
            assert hostComments != null;
            hostComments.setStatus(Status.ACTIVE);
            hostCommentRepository.save(hostComments);
        }
        else {
            accommodationComments.setStatus(Status.ACTIVE);
            accommodationCommentRepository.save(accommodationComments);
        }
        return commentForApproving;
    }

    @Override
    public Comments decline(Comments commentForDeclining) {
        AccommodationComments accommodationComments = accommodationCommentRepository.findById(commentForDeclining.getId()).orElse(null);
        if(accommodationComments == null){
            HostComments hostComments = hostCommentRepository.findById(commentForDeclining.getId()).orElse(null);
            assert hostComments != null;
            hostComments.setStatus(Status.BLOCKED);
            hostCommentRepository.save(hostComments);
        }
        else {
            accommodationComments.setStatus(Status.BLOCKED);
            accommodationCommentRepository.save(accommodationComments);
        }
        return commentForDeclining;
    }

    @Override
    public void delete(Long id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public Comments reportComment(Comments commentForUpdate, Status status) {
        commentForUpdate.setStatus(Status.REPORTED);
        commentsRepository.save(commentForUpdate);
        return commentForUpdate;
    }
}
