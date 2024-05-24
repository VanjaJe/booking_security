package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationDTO;
import com.booking.BookingApp.dto.NotificationSettingsDTO;
import com.booking.BookingApp.mapper.NotificationDTOMapper;
import com.booking.BookingApp.service.interfaces.INotificationService;
import com.booking.BookingApp.service.interfaces.INotificationSettingsService;
import com.booking.BookingApp.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    INotificationService notificationService;

    @Autowired
    IUserService userService;

    @Autowired
    INotificationSettingsService notificationSettingsService;


    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_NOTIFICATIONS')")
    public ResponseEntity<Collection<NotificationDTO>> getUserNotifications(@PathVariable("userId") String id) {
        Collection<Notification> notifications=notificationService.findAllForUser(id);
        Collection<NotificationDTO> notificationDTOS = notifications.stream()
                .map(NotificationDTOMapper::fromNotificationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/new/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_NOTIFICATIONS')")
    public ResponseEntity<NotificationDTO> getUserNewNotifications(@PathVariable("userId") String id) throws ParseException {
        Notification notification=notificationService.findNewForUser(id);
        if(notification==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationtoDTO(notification), HttpStatus.OK);
    }


    @GetMapping(value = "guest/settings/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_GUEST_SETTINGS') or hasAuthority('READ_HOST_SETTINGS')")
    public ResponseEntity<NotificationSettings> getGuestNotificationsSettings(@PathVariable("userId") String id) {
        User user = userService.findOne(id);
        GuestNotificationSettings notificationsSetings = notificationSettingsService.getGuestSettings(user);
        return new ResponseEntity<>(notificationsSetings, HttpStatus.OK);
    }

    @GetMapping(value = "host/settings/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_GUEST_SETTINGS') or hasAuthority('READ_HOST_SETTINGS')")
    public ResponseEntity<NotificationSettings> getHostNotificationsSettings(@PathVariable("userId") String id) {
        User user = userService.findOne(id);
        HostNotificationSettings notificationsSetings = notificationSettingsService.getHostSettings(user);
        return new ResponseEntity<>(notificationsSetings, HttpStatus.OK);
    }

//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
//    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
//        Notification notification = notificationService.findOne(id);
//
//        if (notification == null) {
//            return new ResponseEntity<NotificationDTO>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(notification), HttpStatus.OK);
//    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('WRITE_NOTIFICATION')")
    public ResponseEntity<NotificationDTO> createUserNotification(@RequestBody NotificationDTO notification) throws Exception {
        Notification savedNotification = notificationService.createUserNotification(NotificationDTOMapper.fromDTOtoNotification(notification));
        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(savedNotification), HttpStatus.CREATED);
    }

//    @PutMapping(value = "/guest/{guestId}/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<NotificationDTO>> updateGuestNotificationSettings(@PathVariable("guestId") Long id,
//                                                                               @RequestBody NotificationSettingsDTO settingsDTO) throws Exception {
//
//        Collection<Notification> notifications= notificationService.updateGuestSettings(id,settingsDTO);
//        Collection<NotificationDTO> notificationDTOS = notifications.stream()
//                .map(NotificationDTOMapper::fromNotificationtoDTO)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
//    }
//    @PutMapping(value = "/host/{hostId}/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<NotificationDTO>> updateHostNotificationSettings(@PathVariable("hostId") Long id,
//                                                                              @RequestBody NotificationSettingsDTO settingsDTO) throws Exception {
//
//        Collection<Notification> notifications= notificationService.updateHostSettings(id,settingsDTO);
//        Collection<NotificationDTO> notificationDTOS = notifications.stream()
//                .map(NotificationDTOMapper::fromNotificationtoDTO)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
//    }

    @PutMapping(value = "/{userId}/guestSettings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE_GUEST_SETTINGS')")
    public ResponseEntity<GuestNotificationSettings> updateGuestSettings(@PathVariable("userId") String id, @RequestBody GuestNotificationSettings settings) throws Exception {

        GuestNotificationSettings result = notificationService.updateGuestSettings(id, settings);

        if (result==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<GuestNotificationSettings>(result,HttpStatus.OK);
    }


    @PutMapping(value = "/{userId}/hostSettings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE_HOST_SETTINGS')")
    public ResponseEntity<HostNotificationSettings> updateHostSettings(@PathVariable("userId") String id, @RequestBody HostNotificationSettings settings) throws Exception {

        HostNotificationSettings result = notificationService.updateHostSettings(id, settings);

        if (result==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<HostNotificationSettings>(result,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<NotificationDTO>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{notificationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE_NOTIFICATIONS') ")
    public ResponseEntity<Notification> updateNotification(@PathVariable("notificationId") Long id, @RequestBody Notification notification) throws Exception {

        Notification result = notificationService.updateNotification(id, notification);

        if (result==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Notification>(result,HttpStatus.OK);
    }
}
