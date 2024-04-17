package PKI.dto;

import PKI.domain.Certificate;
import PKI.domain.enums.CertificateType;
import PKI.domain.enums.RequestStatus;
import com.booking.BookingApp.domain.User;
import jakarta.persistence.*;

import java.util.Date;

public class CertificateRequestDTO {
    private Long id;
    private User subject;
    private Certificate certificateIssuer;
    private Date date;
    private RequestStatus requestStatus;
    private CertificateType certificateType;
}
