package com.booking.BookingApp.dto;

import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TimeSlotDTO {
    @Future
    private LocalDate startDate;
    @Future
    private LocalDate endDate;

    public TimeSlotDTO(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TimeSlotDTO{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
