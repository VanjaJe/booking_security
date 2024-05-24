package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.Valid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Address address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = {CascadeType.ALL})
    private Account account;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column(name="activation_link")
    private String activationLink;

    @Column(name="activation_link_date")
    private LocalDate activationLinkDate;

    @Column(name="reporting_reason")
    private String reportingReason;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;

    @ElementCollection
    private List<String> images;

    public User(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account,Boolean deleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.deleted = deleted;
    }

    public User(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account, Timestamp lastPasswordResetDate, String activationLink, LocalDate activationLinkDate, boolean deleted, List<String> image, String reportingReason) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.activationLink = activationLink;
        this.activationLinkDate = activationLinkDate;
        this.deleted = deleted;
        this.images = image;
        this.reportingReason = reportingReason;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", account=" + account +
                ",deleted" + deleted +
                '}';
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<Role> userTypeList=new ArrayList<>();
////        System.out.println("ROLEEEEEE" + this.account.getRole().getName());
//        userTypeList.add(this.account.getRole());
        return this.account.getRoles();
    }

    @Override
    public String getPassword() {
        return null;
    }

//    @Override
//    public String getPassword() {
//        return this.account.getPassword();
//    }
//    public void setPassword(String password) {
//        Timestamp now = new Timestamp(new Date().getTime());
//        this.setLastPasswordResetDate(now);
//        this.account.setPassword(password);
//    }

    @Override
    public String getUsername() {
        return this.account.getUsername();
    }
    public void setUsername(String username) {
        if (this.account == null) {
            this.account = new Account();
        }
        this.account.setUsername(username);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
//        if (this.account.getStatus().equals(Status.ACTIVE)){
//            return true;
//        }
//        return false;
        return true;
    }


    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setImage(String image) {
        this.images.add(image);
    }
}
