package com.booking.BookingApp.mapper;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public ReportDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Report fromDTOToReport(UserDTO dto) {
        return modelMapper.map(dto, Report.class);
    }

    public static ReportDTO fromReportToDTO(Report model) {
        return modelMapper.map(model, ReportDTO.class);
    }
}
