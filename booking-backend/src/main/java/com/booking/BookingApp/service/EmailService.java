package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserService userService;

    public void sendEmail(String to, String subject, String username) {
        System.out.println("IDDDDDDDDDDDD" + username);

        String activationLink = UUID.randomUUID().toString();

        userService.updateActivationLink(activationLink, username);

        String emailText = "Visit  " + "http://localhost:4200/account-activation/" + activationLink + "/" + username
                + "  to activate your account!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("travelbee.team22@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(emailText);
        emailSender.send(message);
    }
}
