package PKI.mapper;

import PKI.domain.Certificate;
import PKI.dto.CertificateDTO;
import com.booking.BookingApp.domain.AccommodationComments;
import com.booking.BookingApp.dto.AccommodationCommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CertificateDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public CertificateDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Certificate fromDTOtoCertificate(CertificateDTO dto) {
        return modelMapper.map(dto, Certificate.class);
    }

    public static CertificateDTO fromCertificatetoDTO(Certificate dto) {
        return modelMapper.map(dto, CertificateDTO.class);
    }
}
