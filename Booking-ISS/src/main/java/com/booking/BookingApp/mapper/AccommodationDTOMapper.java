package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.dto.CreateAccommodationDTO;
import com.booking.BookingApp.dto.EditAccommodationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public AccommodationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Accommodation fromDTOtoAccommodation(AccommodationDTO dto) {
        return modelMapper.map(dto, Accommodation.class);
    }

    public static Accommodation fromEditDTOtoAccommodation(EditAccommodationDTO dto) {
        return modelMapper.map(dto, Accommodation.class);
    }

    public static Accommodation fromCreateDTOtoAccommodation(CreateAccommodationDTO dto) {
        return modelMapper.map(dto, Accommodation.class);
    }

    public static AccommodationDTO fromAccommodationtoDTO(Accommodation dto) {
        return modelMapper.map(dto, AccommodationDTO.class);
    }

//    public static AccommodationDTO fromAccommodationtoCreateDTO(Accommodation dto) {
//        return modelMapper.map(dto, CreateAccommodationDTO.class);
//    }
}
