package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReportService implements IReportService {

    @Autowired
    RequestService requestService;
    @Autowired
    AccommodationService accommodationService;


    @Override
    public Collection<Report> findAllByTimeSlot(String hostId,TimeSlot timeslot) {
        Collection<Report>reports=new ArrayList<>();
        Collection<Accommodation>hostAccommodations=accommodationService.findAll(null,null,
                0,null,0,0,null,null,null,null,
                hostId);
        for(Accommodation accommodation:hostAccommodations){
            Collection<Request>reservations=requestService.findByHostId(hostId,RequestStatus.ACCEPTED,timeslot.getStartDate(),
                    timeslot.getEndDate(),accommodation.getName());
            int sum=0;
            for(Request reservation:reservations){
                LocalDate startDate = reservation.getTimeSlot().getStartDate();
                LocalDate endDate = reservation.getTimeSlot().getEndDate();
                long reservationDays= ChronoUnit.DAYS.between(startDate, endDate) + 1;
                if(endDate.isBefore(LocalDate.now())){
                    LocalDate start = startDate.isBefore(timeslot.getStartDate()) ? timeslot.getStartDate() : startDate;
                    LocalDate end = endDate.isAfter(timeslot.getEndDate()) ? timeslot.getEndDate() : endDate;
                    long days= ChronoUnit.DAYS.between(start, end) + 1;
                    sum+= (int) ((int)(reservation.getPrice()/reservationDays)*days);
                }
            }
            Report report=new Report(accommodation.getId(),accommodation.getName(),sum,reservations.size(),new double[]{});
            reports.add(report);
        }
        return reports;
    }

    @Override
    public Report findAnnualByAccommodation(String accommodationName, int year) {
        Report report = new Report();
        double[] profitByMonth = new double[12];

        Collection<Request> acceptedRequests = requestService.findReservationsByYear(accommodationName,year);

        for (Request request : acceptedRequests) {
            if(request.getTimeSlot().getEndDate().isBefore(LocalDate.now())){
                LocalDate startDate = request.getTimeSlot().getStartDate();
                LocalDate endDate = request.getTimeSlot().getEndDate();
                double profit = request.getPrice();
                int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
                double profitPerNight = profit / totalDays;

                if (startDate.getYear() != endDate.getYear()) {
                    if(startDate.getYear()==year){
                        endDate = endDate.withYear(year).with(TemporalAdjusters.lastDayOfYear());
                    }else{
                        startDate = startDate.withYear(year).withDayOfYear(1);
                    }
                }

                int startMonth = startDate.getMonthValue()-1;
                int endMonth = endDate.getMonthValue()-1;

                if (startMonth == endMonth) {
                    int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
                    profitByMonth[startMonth] += days * profitPerNight;
                } else {

                    for (int day = startDate.getDayOfMonth(); day <= startDate.lengthOfMonth(); day++) {
                        profitByMonth[startMonth] += profitPerNight;
                    }

                    for (int day = 1; day <= endDate.getDayOfMonth(); day++) {
                        profitByMonth[endMonth] += profitPerNight;
                    }
                }
            }
        }
        report.setAccommodationName(accommodationName);
        report.setProfitByMonth(profitByMonth);
        return report;
    }
}