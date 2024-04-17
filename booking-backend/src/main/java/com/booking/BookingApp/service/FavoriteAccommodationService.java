package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;
import com.booking.BookingApp.service.interfaces.IFavoriteAccommodationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FavoriteAccommodationService implements IFavoriteAccommodationService {
    @Override
    public FavoriteAccommodation findOne(Long id) {
        return new FavoriteAccommodation(1L, 101L, 1L);
    }

    @Override
    public Collection<FavoriteAccommodation> findAllForGuest(Long id) {
        return data();
    }

    @Override
    public FavoriteAccommodation create(FavoriteAccommodation favoriteAccommodation) throws Exception {
        return new FavoriteAccommodation(1L, 101L, 1L);
    }

    @Override
    public void delete(Long id) {}

    public Collection<FavoriteAccommodation> data() {
        Collection<FavoriteAccommodation> favoritesList = new ArrayList<>();

        // Add instances of FavoriteAccommodation to the list
        favoritesList.add(new FavoriteAccommodation(1L, 101L, 1L));
        favoritesList.add(new FavoriteAccommodation(2L, 102L, 2L));
        favoritesList.add(new FavoriteAccommodation(3L, 103L, 3L));

        return favoritesList;
    }
}
