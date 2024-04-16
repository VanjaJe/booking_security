package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
//@MappedSuperclass
public class Comments {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "comment_text")
    private String text;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "rating")
    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "commentStatus")
    private Status status;

//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @ManyToOne(cascade=CascadeType.REMOVE)
    @ManyToOne
    private Guest guest;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;

    public Comments(Long id, String text, LocalDate date, double rating, Status status, Guest guest, boolean deleted) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
        this.deleted = deleted;
    }
    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", rating=" + rating +
                ", status=" + status +
                ", guest=" + guest +
                ", deleted=" + deleted +
                '}';
    }
}
