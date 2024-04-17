package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Role;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class AccountDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;


    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @NotEmpty(message = "Username cannot be empty")
    private String username;


    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 8 characters long")
    private String password;


    @NotNull(message = "Status cannot be null")
    private Status status;

    @NotNull
    @NotEmpty(message = "Roles cannot be empty")
    private List<Role> roles;


    public AccountDTO(Long id, String email, String password, Status status, List<Role> roles) {
        this.id=id;
        this.username = email;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public AccountDTO( String email, String password, Status status, List<Role> roles) {
        this.username = email;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + username + '\'' +
                ", password=" + password +
                ", status=" + status +
                ", role=" + roles +
                '}';
    }
}
