package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationSettingsDTO;
import com.booking.BookingApp.repository.*;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class NotificationService implements INotificationService{

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;

    @Autowired
    HostNotificationSettingsRepository hostNotificationSettingsRepository;


    @Autowired
    GuestNotificationSettingsRepository guestNotificationSettingsRepository;

    @Override
    public Collection<Notification> findAllForUser(Long userId) {
        return notificationRepository.findAllByUser_IdAndReadIsFalse(userId);

//        User user = userService.findOne(userId);
//        Collection<Notification> displayedNotifications = new ArrayList<>();
//
//        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
//            GuestNotificationSettings guestSettings = guestNotificationSettingsRepository.findByUser_Id(userId);
//
//            for (Notification notification : notifications) {
//                if (notification.getType().equals(NotificationType.RESERVATION_RESPONSE)) {
//                    if (guestSettings.isRequestResponded()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//            }
//        }
//        else {
//            HostNotificationSettings hostSettings = hostNotificationSettingsRepository.findByUser_Id(userId);
//
//            for (Notification notification : notifications) {
//                if (notification.getType().equals(NotificationType.HOST_RATED)) {
//                    if (hostSettings.isRated()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//                if (notification.getType().equals(NotificationType.ACCOMMODATION_RATED)) {
//                    if (hostSettings.isAccommodationRated()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//                if (notification.getType().equals(NotificationType.RESERVATION_REQUEST)) {
//                    if (hostSettings.isReservationCancelled()) {
//                        displayedNotifications.add(notification);
//                    }
//                }if (notification.getType().equals(NotificationType.RESERVATION_CANCELLED)) {
//                    if (hostSettings.isReservationCancelled()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//            }
//        }
    }


    @Override
    public Notification createUserNotification(Notification notification) throws Exception {
        System.out.println(notification.getDate());
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Long id) {}

    @Override
    public GuestNotificationSettings updateGuestSettings(Long id, GuestNotificationSettings settings) {
        GuestNotificationSettings notificationSettings = guestNotificationSettingsRepository.findByUser_Id(id);
        if (notificationSettings == null) {
            return null;
        }
//        notificationSettings.setRequestResponded(!notificationSettings.isRequestResponded());
        notificationSettings.setRequestResponded(settings.isRequestResponded());
        return guestNotificationSettingsRepository.save(notificationSettings);
    }

    @Override
    public HostNotificationSettings updateHostSettings(Long id, HostNotificationSettings settings) {
        HostNotificationSettings notificationSettings = hostNotificationSettingsRepository.findByUser_Id(id);

        if (notificationSettings == null) {
            return null;
        }

        notificationSettings.setRated(settings.isRated());
        notificationSettings.setReservationCancelled(settings.isReservationCancelled());
        notificationSettings.setRequestCreated(settings.isRequestCreated());
        notificationSettings.setAccommodationRated(settings.isAccommodationRated());
        return hostNotificationSettingsRepository.save(notificationSettings);
    }

    @Override
    public Notification updateNotification(Long id, Notification newNotification) {
        Notification notification=notificationRepository.findById(id).orElse(null);
        if(notification==null){
            return null;
        }
        notification.setRead(newNotification.isRead());
        return notificationRepository.save(notification);

    }


    public Date secondsCalculator(Date date, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    @Override
    public Notification findNewForUser(Long userId) throws ParseException {
        Collection<Notification> notifications = findAllForUser(userId);
        List<Notification>notificationList=notifications.stream().toList();
        if(notificationList.isEmpty()){
            return null;
        }
        Notification notification = notificationList.get(notificationList.size()-1);
        String notifTime = notification.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean enabled = false;
        if (sdf.parse(notifTime).after(secondsCalculator(new Date(), -5))) {
            User user = this.userService.findOne(userId);
            if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
                GuestNotificationSettings guestSettings=guestNotificationSettingsRepository.findByUser_Id(userId);
                enabled = guestSettings.isRequestResponded();
            } else if (user.getAccount().getRoles().get(0).getName().equals("ROLE_HOST")) {
                HostNotificationSettings hostSettings=hostNotificationSettingsRepository.findByUser_Id(userId);

                if (notification.getType() == NotificationType.RESERVATION_REQUEST){
                    enabled = hostSettings.isRequestCreated();
                } else if (notification.getType() == NotificationType.RESERVATION_CANCELLED){
                    enabled =hostSettings.isReservationCancelled();
                } else if (notification.getType() == NotificationType.HOST_RATED){
                    enabled = hostSettings.isRated();
                } else if (notification.getType() == NotificationType.ACCOMMODATION_RATED){
                    enabled = hostSettings.isAccommodationRated();
                }
            }
            if(enabled) {
                return notification;
            }
        }
        return null;
    }

//    @Override
//    public Collection<Notification> updateSettings(Long id, NotificationType type) {
//        return null;
//    }


//    public Collection<Notification> data() {
//        Collection<Notification> notificationList = new ArrayList<>();
//
//        notificationList.add(new Notification(1L, "New message received", LocalDate.now(), NotificationType.HOST_RATED,false));
//        notificationList.add(new Notification(2L, "Reminder: Meeting at 10 AM", LocalDate.now(), NotificationType.RESERVATION_REQUEST,false));
//        notificationList.add(new Notification(3L, "System update available", LocalDate.now(), NotificationType.ACCOMMODATION_RATED,false));
//
//        return notificationList;
//    }
}
