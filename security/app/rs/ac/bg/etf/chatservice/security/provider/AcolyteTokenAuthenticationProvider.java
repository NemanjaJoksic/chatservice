/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.security.exception.AuthenticationException;
import rs.ac.bg.etf.chatservice.security.exception.InvalidTokenException;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authority;
import rs.ac.bg.etf.chatservice.security.model.authentication.SimpleAuthority;
import rs.ac.bg.etf.chatservice.security.model.user.User;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;

/**
 *
 * @author joksin
 */
@Service
public class AcolyteTokenAuthenticationProvider extends TokenAuthenticationProvider {

    private static final String PUBLIC_KEY
            = "-----BEGIN PUBLIC KEY-----"
            + "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEArRo/NDpObIafpSdv4Dl0"
            + "E6vEhL76jGJVFBGPqXgzRoM6oKYySSmORchffR7Xwt0GLy0cJ9ETzus0Y96BezY+"
            + "FnjDT7EImlZf2Dc0NCg/zwCJdU7026Kx7D5l3Vsav3JPKzvp0vyWi8xONIzZyeIA"
            + "/ArkszBfGtzuu061NylTQBwwhDgZcLxqy8eFwoDpCyUSEn48qm5LdQPa278/THqw"
            + "IITT6UX0tXsCl3NjuoZXudWhRdZ+E1EVJDqJ8kla/e0L5GoFk78F3UR6CiVkbJ0I"
            + "9LzAdBd7PxSotktJmEmqEFM/dPpaOsEHzMD0W/WEHmr0AZTPpQn0yTarL0kvISQO"
            + "d//K9m1WRKhLtnK/svW4hvLtz4hId1L7WFRrkFnXSuWJprnUbahalzeLKZUWObNU"
            + "vdU67sMwgOMQNjycpjmwTS9j7NArc1uRQ4jxwQZu0/5IDY1czYH0urs/6R/Knb9U"
            + "oh7TSOSpqIQLRmBNApvK3PDETPD4BtpF49vDqM0N1GLFCroTh+W4p/pHS3X9OSCp"
            + "nCGO+9kCht9Wzn1gaSRQeSHWkVlXIS0iTvA9abMKaLHCOm70n1Hhn1wSs9uKxpHV"
            + "jgljX9eGblq8cmuLrrWAk3Ffi8ELzLmmC4/67/2xpVJ1+6ojl5TMAkM8aKTuu9QQ"
            + "59vFx7wSvDI7Cy9tqE+nTpsCAwEAAQ=="
            + "-----END PUBLIC KEY-----";

    private PublicKey publicKey;
    
    @PostConstruct
    public void iniPublicKey() throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(stripBeginEnd(PUBLIC_KEY)));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(spec);
    }
    
    @Override
    protected UserDetails decodeToken(String token) throws AuthenticationException {
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            Claims payload = jwsClaims.getBody();
            String username = payload.get("username", String.class);
            String[] scopes = payload.get("scope", String.class).split(",");
            List<Authority> roles = getAuthorities(scopes);
            return new User(username, null, roles);
        } catch (Exception ex) {
            throw new InvalidTokenException(ex.getMessage());
        }
    }

    private String stripBeginEnd(String pem) {
        String stripped = pem.replaceAll("-----BEGIN PUBLIC KEY-----", "");
        stripped = stripped.replaceAll("-----END PUBLIC KEY-----", "");
        stripped = stripped.replaceAll("\r\n", "");
        stripped = stripped.replaceAll("\n", "");
        return stripped.trim();
    }

    private List<Authority> getAuthorities(String[] scopes) {
        List<Authority> authorities = new LinkedList<>();
        for(String scope : scopes)
            authorities.add(new SimpleAuthority(scope));
        return authorities;
    }
    
}
