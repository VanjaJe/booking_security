package PKI.service;

import PKI.domain.CertificateRequest;
import PKI.dto.CertificateRequestDTO;
import PKI.service.interfaces.ICertificateRequestService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CertificateRequestService implements ICertificateRequestService {

    @Override
    public CertificateRequest createRequest(CertificateRequest request) {
        return null;
    }

    @Override
    public Collection<CertificateRequest> getAllRequests() {
        return null;
    }

    @Override
    public CertificateRequest acceptRequest(CertificateRequest request) {
        return null;
    }

    @Override
    public CertificateRequest declineRequest(CertificateRequest request) {
        return null;
    }
}