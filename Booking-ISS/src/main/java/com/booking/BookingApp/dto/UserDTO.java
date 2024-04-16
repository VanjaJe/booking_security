package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;
  
    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Size(min = 3, message = "First name must not have minimum 3 characters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Size(min = 3, message = "Last name must not have minimum 3 characters")
    private String lastName;

    @Valid
    private AddressDTO address;

    @Pattern(regexp = "\\d+", message = "Phone number must contain only numeric digits")
    @NotEmpty
    private String phoneNumber;

    @Valid
    private AccountDTO account;

    private String reportingReason;

    public UserDTO(Long id, String firstName, String lastName, AddressDTO address, String phoneNumber, AccountDTO accountDTO, String reportingReason) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = accountDTO;
        this.reportingReason = reportingReason;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressDTO=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountDTO=" + account +
                '}';
    }
}
