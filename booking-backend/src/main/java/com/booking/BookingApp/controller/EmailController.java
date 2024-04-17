package com.booking.BookingApp.controller;


import com.booking.BookingApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
//    @PreAuthorize("hasRole('GUEST') && hasRole('HOST')")
    public String sendEmail(@RequestParam("username") String username) {
        emailService.sendEmail("jevtic.valentina02@gmail.com", "Account activation", username);
        return  "Email sent successfully!";
    }
}
