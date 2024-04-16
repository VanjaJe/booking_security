package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.dto.CommentsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentsDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public CommentsDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Comments fromDTOtoComments(CommentsDTO dto) {
        return modelMapper.map(dto, Comments.class);
    }

    public static CommentsDTO fromCommentstoDTO(Comments dto) {
        return modelMapper.map(dto, CommentsDTO.class);
    }
}
