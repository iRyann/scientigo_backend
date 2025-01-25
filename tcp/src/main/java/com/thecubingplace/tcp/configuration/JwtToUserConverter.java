package com.thecubingplace.tcp.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Arrays.stream(jwt.getClaimAsString("roles")
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        CustomUserDetails userDetails = new CustomUserDetails(
            jwt.getSubject(),
            "",  // pas besoin du mot de passe ici
            authorities,
            Long.parseLong(jwt.getClaimAsString("userId"))
        );
        
        return new UsernamePasswordAuthenticationToken(userDetails, jwt, authorities);
    }
}