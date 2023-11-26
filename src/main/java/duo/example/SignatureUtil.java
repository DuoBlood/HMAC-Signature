package duo.example;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class SignatureUtil {

    private static final String HMAC_SHA512 = "HmacSHA512";

    private int secretKeyHash;
    private Mac sha512Hmac;

    private Mac getMac(final String secretKey) {
        if (this.secretKeyHash == secretKey.hashCode() && sha512Hmac != null) {
            return sha512Hmac;
        } else {
            this.secretKeyHash = secretKey.hashCode();
            try {
                sha512Hmac = Mac.getInstance(HMAC_SHA512);
                final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
                sha512Hmac.init(keySpec);
                return sha512Hmac;
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String toHexString(final byte[] bytes) {
        final Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public String encode(final String secretKey, final String src, final String sol) {
        final Mac sha512Hmac = getMac(secretKey);
        final String raw = src + sol;
        final byte[] macData = sha512Hmac.doFinal(raw.getBytes(StandardCharsets.UTF_8));
        return toHexString(macData);
    }
}
