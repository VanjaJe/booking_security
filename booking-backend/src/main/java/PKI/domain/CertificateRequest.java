package PKI.domain;

import PKI.domain.enums.CertificateType;
import PKI.domain.enums.RequestStatus;
import com.booking.BookingApp.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CertificateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User subject;

    @ManyToOne(fetch = FetchType.EAGER)
    private Certificate certificateIssuer;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private RequestStatus requestStatus;

    @Column(name = "type")
    private CertificateType certificateType;

}
