package se.jensen.simeon.productservice.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtUtil {

    private final PublicKey publicKey;

    public JwtUtil(@Value("${jwt.public-key}") String publicKeyStr) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] publicBytes = Base64.getDecoder().decode(publicKeyStr.replaceAll("\\s", ""));
        this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}