package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.AccommodationComments;
import com.booking.BookingApp.domain.HostComments;
import com.booking.BookingApp.dto.AccommodationCommentDTO;
import com.booking.BookingApp.dto.CreateHostCommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationCommentDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public AccommodationCommentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static AccommodationComments fromDTOtoComments(AccommodationCommentDTO dto) {
        return modelMapper.map(dto, AccommodationComments.class);
    }

    public static AccommodationCommentDTO fromCommentstoDTO(AccommodationComments dto) {
        return modelMapper.map(dto, AccommodationCommentDTO.class);
    }
}
