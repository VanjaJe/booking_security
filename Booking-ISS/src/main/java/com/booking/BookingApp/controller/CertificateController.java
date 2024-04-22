package com.booking.BookingApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @PostMapping("/download-certificate")
    public ResponseEntity<String> receiveCertificate(@RequestBody byte[] certificateBytes) {
        try {
            System.out.println();
            System.out.println("Received certificate bytes: ");
            System.out.println(new String(certificateBytes));
            System.out.println();

            return ResponseEntity.ok("Certificate uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload certificate");
        }
    }
}
