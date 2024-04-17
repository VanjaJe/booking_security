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

    User findOne(Long id);

    Collection<User> findByStatus(Status userStatus);

    boolean activateUser(String activationLink, String username);

    User update(User user) throws Exception;

    void delete(Long id);

    void deleteHost(User user);

    void deleteGuest(User user);

    Collection<Accommodation> findFavorites(Long id);

//    User save(User user);
    User saveGuest(User user);

    User saveHost(User user);

    User updateActivationLink(String activationLink, String username);

    void uploadImage(Long userId, MultipartFile image) throws IOException;

    List<String> getImages(Long userId) throws IOException;

    Collection<User> findAllByStatus(Status status);

    User reportUser(User status, Long id);

    User reportHost(User status, Long id);

    User reportGuest(User status, Long id);

    void updateFavoriteAccommodations(Long guestId, Long accommodationId);

    User block(Long userId);
}
