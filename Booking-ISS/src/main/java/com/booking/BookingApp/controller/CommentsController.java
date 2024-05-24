package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.*;
import com.booking.BookingApp.mapper.*;
import com.booking.BookingApp.service.CommentService;
import com.booking.BookingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Collection<CommentsDTO>> getComments(@RequestParam(value="status", required = false) Status status) {
        Collection<Comments> comments = commentService.findAll(status);
//        Collection<AccommodationComments> accommodationComments = commentService.findAllAccommodationComments(status);
//        Collection<HostComments> hostComments = commentService.findAllHostComments(status);
        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/hosts", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Collection<CreateHostCommentDTO>> getAllHostComments(@RequestParam(value = "status", required = false) Status status) {
        Collection<HostComments> comments = commentService.findAllHostComments(status);

        Collection<CreateHostCommentDTO> commentsDTO = comments.stream()
                .map(HostCommentDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CreateHostCommentDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Collection<AccommodationCommentDTO>> getAllAccommodationComments(@RequestParam(value = "status", required = false) Status status) {
        Collection<AccommodationComments> comments = commentService.findAllAccommodationComments(status);

        Collection<AccommodationCommentDTO> commentsDTO = comments.stream()
                .map(AccommodationCommentDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<AccommodationCommentDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<CommentsDTO> getById(@PathVariable("id") Long id) {
        Comments comment = commentService.findById(id);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(comment), HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Collection<CommentsDTO>> getHostComments(@PathVariable("hostId") String id,
                                                                   @RequestParam(value = "status", required = false) Status status) {
        Collection<Comments> comments = commentService.findByHostId(id, status);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Collection<CommentsDTO>> getAccommodationComments(@PathVariable("accommodationId") Long id,
                                                                            @RequestParam(value = "status", required = false) Status status) {
        Collection<Comments> comments = commentService.findByAccommodationId(id, status);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}/averageRate", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Double> getHostRating(@PathVariable("hostId") String id) {
        double rating = commentService.findHostRating(id);
        return new ResponseEntity<Double>(rating,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}/averageRate", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    public ResponseEntity<Double> getAccommodationRating(@PathVariable("accommodationId") Long id) {
        double rating = commentService.findAccommodationRating(id);
        return new ResponseEntity<Double>(rating,HttpStatus.OK);
    }

    @PostMapping(value = "/host/{hostId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('WRITE_COMMENT')")
    public ResponseEntity<CommentsDTO> createHostComment(@RequestBody CommentsDTO commentDTO,
                                                         @PathVariable("hostId") String id) {
        Comments commentModel = CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments savedComment = commentService.createHostComment(commentModel, id);
        if (savedComment==null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(savedComment), HttpStatus.CREATED);
    }

    @PostMapping(value = "/accommodation/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('WRITE_COMMENT')")
    public ResponseEntity<CommentsDTO> createAccommodationComment(@RequestBody CommentsDTO commentDTO,
                                                                  @PathVariable("accommodationId") Long id) {
        Comments commentModel = CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments savedComment = commentService.createAccommodationComment(commentModel, id);
        if (savedComment==null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(savedComment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> updateComment(@RequestBody CommentsDTO commentDTO, @PathVariable("id") Long id) {
        Comments comment= CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments updatedComment = commentService.update(comment);
        if (updatedComment == null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CommentsDTO>(CommentsDTOMapper.fromCommentstoDTO(updatedComment), HttpStatus.OK);
    }

    @PutMapping(value = "/approve/accommodations/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_COMMENT')")
    public ResponseEntity<CommentsDTO> approveAccommodationsComment(@RequestBody AccommodationCommentDTO commentDTO) {
        AccommodationComments comment= AccommodationCommentDTOMapper.fromDTOtoComments(commentDTO);
        Comments updatedComment = commentService.approve(comment);
        if (updatedComment == null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CommentsDTO>(CommentsDTOMapper.fromCommentstoDTO(updatedComment), HttpStatus.OK);
    }

    @PutMapping(value = "/decline/accommodations/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_COMMENT')")
    public ResponseEntity<CommentsDTO> declineAccommodationsComment(@RequestBody AccommodationCommentDTO commentDTO) {
        AccommodationComments comment= AccommodationCommentDTOMapper.fromDTOtoComments(commentDTO);
        Comments updatedComment = commentService.decline(comment);
        if (updatedComment == null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CommentsDTO>(CommentsDTOMapper.fromCommentstoDTO(updatedComment), HttpStatus.OK);
    }

    @PutMapping(value = "/approve/hosts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_COMMENT')")
    public ResponseEntity<CommentsDTO> approveHostsComment(@RequestBody CreateHostCommentDTO commentDTO) {
        HostComments comment= HostCommentDTOMapper.fromDTOtoComments(commentDTO);
        Comments updatedComment = commentService.approve(comment);
        if (updatedComment == null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CommentsDTO>(CommentsDTOMapper.fromCommentstoDTO(updatedComment), HttpStatus.OK);
    }

    @PutMapping(value = "/decline/hosts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_COMMENT')")
    public ResponseEntity<CommentsDTO> declineHostsComment(@RequestBody CreateHostCommentDTO commentDTO) {
        HostComments comment= HostCommentDTOMapper.fromDTOtoComments(commentDTO);
        Comments updatedComment = commentService.decline(comment);
        if (updatedComment == null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CommentsDTO>(CommentsDTOMapper.fromCommentstoDTO(updatedComment), HttpStatus.OK);
    }

    @PutMapping(value = "reportComment/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('REPORT_COMMENT')")
    public ResponseEntity<CommentsDTO> reportComment(@RequestBody Status status, @PathVariable("commentId") Long commentId) {
        Comments commentForUpdate = commentService.findById(commentId);
        Comments updatedComment = commentService.reportComment(commentForUpdate, status);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(updatedComment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_COMMENT')")
    public ResponseEntity<CommentsDTO> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<CommentsDTO>(HttpStatus.NO_CONTENT);
    }
}