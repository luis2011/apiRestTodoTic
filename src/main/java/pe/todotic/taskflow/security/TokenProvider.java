package pe.todotic.taskflow.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    @Value("${app.security.jwt.token-validity-in-seconds}")
    private Long tokenValidityInSeconds;

    @Value("${app.security.jwt.secret}")
    private String tokenSecret;

    private byte[] secretBytes;
    private SecretKey key;

    @PostConstruct
    public void init() {
        secretBytes = Decoders.BASE64.decode(tokenSecret);
        key = Keys.hmacShaKeyFor(secretBytes);
    }

    public String crearToken(Authentication authentication) {
        long fechaActual = new Date().getTime();
        Date fechaExpiracion = new Date(fechaActual + (tokenValidityInSeconds * 1_000));
        String autoridades = authentication
                .getAuthorities() // roles, permisos -> ROLE_, X
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth", autoridades)
                .setExpiration(fechaExpiracion)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication obtenerAuthentication(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            Claims claims = jwtParser.parseClaimsJws(token).getBody();

            List<SimpleGrantedAuthority> authorities = Arrays
                    .stream(claims.get("auth").toString().split(","))
                    .filter(auth -> !auth.trim().isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } catch (JwtException e) {
            return null;
        }
    }

}
