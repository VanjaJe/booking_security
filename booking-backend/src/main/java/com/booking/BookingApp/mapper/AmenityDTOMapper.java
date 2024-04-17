package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmenityDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public AmenityDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Amenity fromDTOtoAmenity(AmenityDTO dto) {
        return modelMapper.map(dto, Amenity.class);
    }

    public static AmenityDTO fromAmenitytoDTO(Amenity dto) {
        return modelMapper.map(dto, AmenityDTO.class);
    }
}
