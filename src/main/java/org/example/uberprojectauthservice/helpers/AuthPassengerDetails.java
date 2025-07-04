package org.example.uberprojectauthservice.helpers;

import org.example.uberprojectauthservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Why, we need this class?
// Because Spring security works on UserDetails Polymorphic type for auth

public class AuthPassengerDetails extends Passenger implements UserDetails {

    private String userName; // email // name // id

    private String password;

    public AuthPassengerDetails(Passenger passenger) {
        this.userName = passenger.getEmail();
        this.password = passenger.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
    //Below, a set of methods are few concerns

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
