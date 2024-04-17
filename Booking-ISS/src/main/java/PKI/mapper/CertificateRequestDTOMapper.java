package PKI.mapper;

import PKI.domain.Certificate;
import PKI.domain.CertificateRequest;
import PKI.dto.CertificateDTO;
import PKI.dto.CertificateRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CertificateRequestDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public CertificateRequestDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static CertificateRequest fromDTOtoCertificateRequest(CertificateRequestDTO dto) {
        return modelMapper.map(dto, CertificateRequest.class);
    }

    public static CertificateRequestDTO fromCertificateRequestToDTO(CertificateRequest dto) {
        return modelMapper.map(dto, CertificateRequestDTO.class);
    }
}
