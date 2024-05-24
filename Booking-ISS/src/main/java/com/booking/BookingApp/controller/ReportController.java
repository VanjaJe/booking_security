package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.mapper.AccommodationDTOMapper;
import com.booking.BookingApp.mapper.ReportDTOMapper;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_REPORT')")
    public ResponseEntity<Collection<ReportDTO>> getReportByTimeSlot(
            @RequestParam(value = "hostId", required = false)String hostId,
            @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        TimeSlot timeSlot = new TimeSlot(begin.toInstant().atZone
                (ZoneId.systemDefault()).toLocalDate(),
                end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),false);
        Collection<Report> reports=reportService.findAllByTimeSlot(hostId,timeSlot);
        Collection<ReportDTO> reportDTOS = reports.stream()
                .map(ReportDTOMapper::fromReportToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<ReportDTO>>(reportDTOS, HttpStatus.OK);
    };

    @GetMapping(value = "/annual",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_REPORT')")
    public ResponseEntity<ReportDTO> getAnnualReportByAccommodation(
            @RequestParam("accommodationName") String accommodationName,
            @RequestParam("year") int year) {
        Report report=reportService.findAnnualByAccommodation(accommodationName,year);
        return new ResponseEntity<ReportDTO>(ReportDTOMapper.fromReportToDTO(report), HttpStatus.OK);
    };
}
