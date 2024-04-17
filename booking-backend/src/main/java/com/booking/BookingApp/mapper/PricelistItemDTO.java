package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.PricelistItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PricelistItemDTO {

    private static ModelMapper modelMapper;

    @Autowired
    public PricelistItemDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static PricelistItem fromDTOtoAmenity(PricelistItemDTO dto) {
        return modelMapper.map(dto, PricelistItem.class);
    }

    public static PricelistItemDTO fromAmenitytoDTO(PricelistItem dto) {
        return modelMapper.map(dto, PricelistItemDTO.class);
    }
}

