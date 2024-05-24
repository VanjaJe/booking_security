package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    Collection<User> findAll();

    User findByUsername(String username);

    User findOne(String id);

    Collection<User> findByStatus(Status userStatus);

    boolean activateUser(String activationLink, String username);

    User update(User user) throws Exception;

    void delete(String id);

    void deleteHost(User user);

    void deleteGuest(User user);

    Collection<Accommodation> findFavorites(String id);

//    User save(User user);
    User saveGuest(User user);

    User saveHost(User user);

    User updateActivationLink(String activationLink, String username);

    void uploadImage(String userId, MultipartFile image) throws IOException;

    List<String> getImages(String userId) throws IOException;

    Collection<User> findAllByStatus(Status status);

    User reportUser(User status, String id);

    User reportHost(User status, String id);

    User reportGuest(User status, String id);

    void updateFavoriteAccommodations(String guestId, Long accommodationId);

    User block(String userId);
}
