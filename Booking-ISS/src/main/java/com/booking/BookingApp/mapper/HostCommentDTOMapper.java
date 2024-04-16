package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.HostComments;
import com.booking.BookingApp.dto.CommentsDTO;
import com.booking.BookingApp.dto.CreateHostCommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class HostCommentDTOMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public HostCommentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static HostComments fromDTOtoComments(CreateHostCommentDTO dto) {
        return modelMapper.map(dto, HostComments.class);
    }

    public static CreateHostCommentDTO fromCommentstoDTO(HostComments dto) {
        return modelMapper.map(dto, CreateHostCommentDTO.class);
    }
}
