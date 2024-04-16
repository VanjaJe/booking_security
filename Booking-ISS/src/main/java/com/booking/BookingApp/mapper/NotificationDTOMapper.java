package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.dto.NotificationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationDTOMapper {
    private static ModelMapper modelMapper;
    @Autowired
    public NotificationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Notification fromDTOtoNotification(NotificationDTO dto) {
        return modelMapper.map(dto, Notification.class);
    }

    public static NotificationDTO fromNotificationtoDTO(Notification dto) {
        return modelMapper.map(dto, NotificationDTO.class);
    }
}
