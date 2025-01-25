package com.thecubingplace.tcp.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.thecubingplace.tcp.repository.UserRepository;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final Long id;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomUserDetails)) return false;
        if (!super.equals(o)) return false;

        CustomUserDetails that = (CustomUserDetails) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    public static CustomUserDetails fromUserDetails(
        org.springframework.security.core.userdetails.User user,
        UserRepository userRepository
    ) {
        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getAuthorities(), userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + user.getUsername())).getId());
    }
    
}