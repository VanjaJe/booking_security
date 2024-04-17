package PKI.service.interfaces;


import PKI.domain.CertificateRequest;
import PKI.dto.CertificateRequestDTO;

import java.util.Collection;

public interface ICertificateRequestService {

    public CertificateRequest createRequest(CertificateRequest request);

    public Collection<CertificateRequest> getAllRequests();

    public CertificateRequest acceptRequest(CertificateRequest request);
    public CertificateRequest declineRequest(CertificateRequest request);

}
