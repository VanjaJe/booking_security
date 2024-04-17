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
    public Collection<Comments> findByHostId(Long id, Status status) {
        System.out.println("DOOOOSAAAAAOAOOAOAOAOAOOAOAOAOAO");
        System.out.println(id);
        System.out.println(hostCommentRepository.findByHost_IdAndStatus(id, status));
        return hostCommentRepository.findByHost_IdAndStatus(id, Status.ACTIVE);
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
    public double findHostRating(Long id) {
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
    public Comments createHostComment(Comments comment, Long id) {
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

    public Collection<Comments> data() {
        Collection<Comments> commentsList = new ArrayList<>();
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1",false);
        Role role=new Role(1L,"guest");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Account account = new Account(1L, "aleksicisidora@yahoo.com","slatkica",Status.ACTIVE, roles, false);
        Guest guest = new Guest(1L,"Isidora","Aleksic",address,"0692104221",account,false, null);
        // Adding instances to the collection
        commentsList.add(new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, guest, false));
        commentsList.add(new Comments(2L, "Agree with you.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(3L, "Disagree with this.", LocalDate.now(), 2.5, Status.ACTIVE,guest, false));
        commentsList.add(new Comments(4L, "Fantastic!", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(5L, "Nothing special.", LocalDate.now(), 3.0, Status.REPORTED, guest, false));
        commentsList.add(new Comments(6L, "I don't like it.", LocalDate.now(), 2.5, Status.ACTIVE,guest, false));
        commentsList.add(new Comments(7L, "It was good!", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(8L, "It's okay for that price.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(9L, "It was dirty.", LocalDate.now(), 2.5, Status.ACTIVE,guest,false));
        commentsList.add(new Comments(10L, "The host is extra.", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(11L, "Not good not bad.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(12L, "Bad.", LocalDate.now(), 2.5, Status.ACTIVE,guest,false));
        commentsList.add(new Comments(13L, "Super!", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(14L, "Fun but I expected more.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(15L, "I'm not returning there.", LocalDate.now(), 2.5, Status.ACTIVE,guest,false));
        return commentsList;
    }
}
