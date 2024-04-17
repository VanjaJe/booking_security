package PKI.dto;

import PKI.domain.enums.CertificateType;
import com.booking.BookingApp.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CertificateDTO {

    private Long id;

    private CertificateType certificateType;

    private Date dateFrom;

    private Date dateTo;

    private User subject;

    private boolean isRevoked;

    private String serialNumber;

    private String issuerSerialNumber;
}
