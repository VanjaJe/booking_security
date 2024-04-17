package com.booking.BookingApp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Report {
    private Long id;
    private Long accommodationId;
    private String accommodationName;
    private int totalProfit;
    private double[] profitByMonth;
    private int numberOfReservations;

    public Report(Long accommodationId,String accommodationName,int total, int numOfRes,double[]monthly) {
        this.accommodationId = accommodationId;
        this.totalProfit = total;
        this.numberOfReservations = numOfRes;
        this.profitByMonth=monthly;
        this.accommodationName=accommodationName;
    }

    @Override
    public String toString() {
        return "Report{" +
                "AccommodationId=" + accommodationId +
                "TotalProfit=" + totalProfit +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}