package com.booking.BookingApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {


    @NotEmpty(message = "Country cannot be empty")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;


    @NotEmpty(message = "City cannot be empty")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;


    @NotEmpty(message = "Address cannot be empty")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;


    public AddressDTO(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
