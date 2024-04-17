package PKI.service;

import PKI.domain.Issuer;
import PKI.domain.Subject;
import PKI.service.interfaces.ICertificateGeneratorService;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.Date;

@Service
public class CertificateGeneratorService implements ICertificateGeneratorService {
    @Override
    public X509Certificate generateCertificate(Subject subject, Issuer issuer, Date startDate, Date endDate, String serialNumber) {
        return null;
    }
}
