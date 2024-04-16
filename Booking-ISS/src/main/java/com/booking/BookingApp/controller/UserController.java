package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.*;
import com.booking.BookingApp.exception.ResourceConflictException;
import com.booking.BookingApp.mapper.AccommodationDTOMapper;
import com.booking.BookingApp.mapper.ReportDTOMapper;
import com.booking.BookingApp.mapper.UserDTOMapper;
import com.booking.BookingApp.service.EmailService;
import com.booking.BookingApp.service.NotificationSettingsService;
import com.booking.BookingApp.service.interfaces.IUserService;
import com.booking.BookingApp.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationSettingsService notificationSettingsService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> getUsers() {
        Collection<User> users = userService.findAll();
        Collection<UserDTO> usersDTO = users.stream()
                .map(UserDTOMapper::fromUsertoDTO)
                .toList();
        return new ResponseEntity<Collection<UserDTO>>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> getUsersByStatus(@RequestParam("status") Status status) {
        Collection<User> users = userService.findAllByStatus(status);
        Collection<UserDTO> usersDTO = users.stream()
                .map(UserDTOMapper::fromUsertoDTO)
                .toList();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getGuestFavorites(@PathVariable("id") Long id) {
        Collection<Accommodation> accommodations= userService.findFavorites(id);
        if (accommodations == null) {
            return new ResponseEntity<Collection<AccommodationDTO>>(HttpStatus.NOT_FOUND);
        }
        Collection<AccommodationDTO> accommodationDTOS = accommodations.stream()
                .map(AccommodationDTOMapper::fromAccommodationtoDTO)
                .toList();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);

    }

    @PostMapping(value ="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody UserCredentialsDTO userCredentials, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentials.getUsername(), userCredentials.getPassword()));


        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();

        if (!(user.getAccount().getStatus() == Status.ACTIVE)) {
            return new ResponseEntity<UserTokenState>(HttpStatus.BAD_REQUEST);
        }

        String jwt = tokenUtils.generateToken(user.getUsername(), user.getAccount().getRoles().get(0), user.getId());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        System.out.println(jwt);
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getAccount().getRoles().get(0).getName(),user.getId()));
    };

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.findByUsername(user.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(user.getId(), "Username already exists");
        }

        User savedUser;
//        User newUser = this.userService.save(user);
        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
            System.out.println("GOST");
            savedUser = userService.saveGuest(user);
        }
        else { savedUser = userService.saveHost(user);}

        notificationSettingsService.createNotificationSettings(savedUser);

        emailService.sendEmail("sonjabaljicki2002@gmail.com", "Account activation", savedUser.getUsername());

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @GetMapping(value ="/log-in", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserDTO> getReportByHostAndTimeSlot(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password) {
//        User user = userService.findLoggedUser(username,password);
//        if (user == null) {
//            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
//    };

//    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<UserDTO>> getUsersByStatus(@PathVariable("status") Status status) {
//        Collection<User> users = userService.findAllByStatus(status);
//        Collection<UserDTO> usersDTO = users.stream()
//                .map(UserDTOMapper::fromUsertoDTO)
//                .toList();
//        return new ResponseEntity<Collection<UserDTO>>(usersDTO, HttpStatus.OK);
//    }

    @GetMapping(value = "/userEmail/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByAccountUsername(@PathVariable("userEmail") String userEmail) {
        User user = userService.findByUsername(userEmail);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
    }

    @GetMapping("/user-account-activation/{activation-link}/{username}")
    public ResponseEntity<String> activateUser(@PathVariable("activation-link") String activationLink, @PathVariable("username") String username){
        boolean isActivated = userService.activateUser(activationLink, username);
        if(isActivated){
            return new ResponseEntity<String>("User account is successfully activated.",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Failed to activate user account.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws Exception {
        User newUser = UserDTOMapper.fromDTOtoUser(userDTO);
//        User savedUser = userService.save(newUser);
        User savedUser;
        if (userDTO.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
            savedUser = userService.saveGuest(newUser);
        }
        else{
            savedUser = userService.saveHost(newUser);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(savedUser), HttpStatus.CREATED);
    }

//    @PutMapping(value = "reportUser/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserDTO> reportUser(@RequestBody Status status, @PathVariable Long id)
//            throws Exception {
//        User updatedUser = userService.reportUser(status, id);
//        if (updatedUser == null) {
//            return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(updatedUser), HttpStatus.OK);
//    }

    @PutMapping(value = "reportUser/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> reportUser(@RequestBody UserDTO reportedUserDTO, @PathVariable("id") Long guestId)
            throws Exception {
        User user = UserDTOMapper.fromDTOtoUser(reportedUserDTO);
        user.setReportingReason(reportedUserDTO.getReportingReason());

        if (user.getAccount().getStatus().equals(Status.REPORTED)) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }

        User updatedUser;
        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
            updatedUser = userService.reportGuest(user, guestId);
        }
        else { updatedUser = userService.reportHost(user, guestId); }

        if (updatedUser == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(updatedUser), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, @PathVariable Long id)
            throws Exception {
        User user = UserDTOMapper.fromDTOtoUser(userDTO);
        User updatedUser = userService.update(user);
        if (updatedUser == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        System.out.println("brisese");
        userService.delete(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
    }

    @PostMapping("/{userId}/upload-picture")
    public ResponseEntity<String> uploadImage(
            @PathVariable("userId") Long userId,
            @RequestParam("images") Collection<MultipartFile> imageFiles) throws IOException {
        System.out.println("kika");

        for(MultipartFile image: imageFiles) {
            System.out.println("kika");
            userService.uploadImage(userId, image);
        }
        return new ResponseEntity<>("Pictures uploaded successfully", HttpStatus.OK);
    }

    @PutMapping("/block/{userId}")
    public ResponseEntity<UserDTO> blockUser(@RequestBody UserDTO userDTO,
                                             @PathVariable("userId") Long userId) {
        System.out.println("dobrodosao na listu block");
        User user = userService.block(userId);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('GUEST') or hasRole('ADMIN')")
    @GetMapping(value = "/{userId}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getImages(@PathVariable("userId") Long userId) throws IOException {
        List<String> images = userService.getImages(userId);
        return ResponseEntity.ok().body(images);
    }

    @PutMapping("/{guestId}/favoriteAccommodations/{accommodationId}")
    public ResponseEntity<String> updateFavorites(
            @PathVariable Long guestId, @PathVariable Long accommodationId) {
        userService.updateFavoriteAccommodations(guestId, accommodationId);
        return new ResponseEntity<String>("Added favorite.",HttpStatus.OK);
    }
}