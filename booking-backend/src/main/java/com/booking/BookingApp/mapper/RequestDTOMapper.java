package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.dto.RequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public RequestDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Request fromDTOtoRequest(RequestDTO dto) {
        return modelMapper.map(dto, Request.class);
    }

    public static RequestDTO fromRequesttoDTO(Request dto) {
        return modelMapper.map(dto, RequestDTO.class);
    }
}
