package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Repositiories.PassengerRepository;
import org.example.uberprojectauthservice.helpers.AuthPassengerDetails;
import org.example.uberprojectauthservice.models.Passenger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * This class is responsible for loading the user in the form of UserDetail object for auth.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PassengerRepository passengerRepository;
    public UserDetailsServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);
        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("User cannot be found with email: " + email);
        }
    }
}
