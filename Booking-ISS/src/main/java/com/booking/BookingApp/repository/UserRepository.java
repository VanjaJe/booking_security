package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.domain.Host;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT g.favoriteAccommodations FROM User u JOIN Guest g ON u.id = g.id WHERE u.id = :guestId")
    Collection<Accommodation> findFavoriteAccommodationsByGuestId(Long guestId);

    User findByAccount_Username(String username);

    Collection<User> findByAccount_Status(Status userStatus);

    Collection<User> findAllByAccountStatus(Status status);
//
//    @Modifying
//    @Query(value = "UPDATE favorite_accommodation SET deleted = true WHERE accommodation_id=:accommodationId", nativeQuery = true)
//    void deleteFavoriteAccommodationsByAccommodationId(@Param("accommodationId") Long accommodationId);
}
