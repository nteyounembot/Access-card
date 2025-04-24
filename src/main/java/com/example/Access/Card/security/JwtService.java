package com.example.Access.Card.security;

import com.example.Access.Card.entities.Utilisateur;
import com.example.Access.Card.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String ENCRYPTION_KEY = "abfb3f68e6c61ddff20b4b0616f0d9171f7503b51e7ad817250c2f0d398cf995";
    private final UtilisateurService utilisateurService;

    public JwtService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Map<String, String> generate(String username) {
        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
        return this.generateJwt(utilisateur);
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

    final  String bearer = Jwts.builder()

                .setSubject(utilisateur.getEmail())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .addClaims(Map.of(
                        "nom", utilisateur.getNom(),
                        Claims.EXPIRATION, new Date(expirationTime),
                        Claims.SUBJECT,  utilisateur.getEmail()
                ))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer", bearer);
    }

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails loadUserByUsername) {
        Date expirationDate = getExpirationDateFromToken(token);
        boolean notExpired = expirationDate.after(new Date());
        System.out.println("is token valid: " + notExpired);
        return notExpired;
    }

}