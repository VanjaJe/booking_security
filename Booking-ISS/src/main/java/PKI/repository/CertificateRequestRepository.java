package PKI.repository;

import PKI.domain.CertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRequestRepository extends JpaRepository<CertificateRequest,Long> {
}
