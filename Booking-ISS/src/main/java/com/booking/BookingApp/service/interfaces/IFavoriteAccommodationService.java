package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;

import java.util.Collection;

public interface IFavoriteAccommodationService {
    FavoriteAccommodation findOne(Long id);
    Collection<FavoriteAccommodation> findAllForGuest(Long id);
    FavoriteAccommodation create(FavoriteAccommodation favoriteAccommodation) throws Exception;
    void delete(Long id);
}
