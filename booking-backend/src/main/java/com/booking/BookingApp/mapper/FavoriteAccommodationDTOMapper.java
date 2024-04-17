package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FavoriteAccommodationDTOMapper {
    private static ModelMapper modelMapper;
    @Autowired
    public FavoriteAccommodationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static FavoriteAccommodation fromDTOtoFavorite(FavoriteAccommodationDTO dto) {
        return modelMapper.map(dto, FavoriteAccommodation.class);
    }

    public static FavoriteAccommodationDTO fromFavoritetoDTO(FavoriteAccommodation dto) {
        return modelMapper.map(dto, FavoriteAccommodationDTO.class);
    }
}
