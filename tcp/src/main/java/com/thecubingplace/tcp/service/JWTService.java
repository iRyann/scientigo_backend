package com.thecubingplace.tcp.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.thecubingplace.tcp.configuration.CustomUserDetails;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JWTService {
    private final JwtEncoder jwtEncoder;

    public JWTService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        
        // Récupérer l'ID de l'utilisateur
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("tcp")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .subject(authentication.getName())
                .claim("userId", userDetails.getId().toString()) // Ajouter l'ID comme String
                .claim("roles", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
            JwsHeader.with(MacAlgorithm.HS256).build(), 
            claims
        );

        return jwtEncoder.encode(parameters).getTokenValue();
    }
}
