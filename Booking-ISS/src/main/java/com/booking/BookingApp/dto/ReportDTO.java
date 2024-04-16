package com.booking.BookingApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {

    private Long id;
    private Long accommodationId;
    private String accommodationName;
    private int totalProfit;
    private double[] profitByMonth;
    private int numberOfReservations;

    public ReportDTO(Long accommodationId,String accommodationName,int total, int numOfRes,double[]monthly) {
        this.accommodationId = accommodationId;
        this.totalProfit = total;
        this.numberOfReservations = numOfRes;
        this.profitByMonth=monthly;
        this.accommodationName=accommodationName;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "AccommodationId=" + accommodationId +
                "TotalProfit=" + totalProfit +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}
