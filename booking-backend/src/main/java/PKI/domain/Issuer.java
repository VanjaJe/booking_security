package PKI.domain;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.x500.X500Name;

public class Issuer {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private X500Name x500Name;
}
