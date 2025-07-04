package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Repositiories.PassengerRepository;
import org.example.uberprojectauthservice.dto.PassengerDto;
import org.example.uberprojectauthservice.dto.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.models.Passenger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
        public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
            Passenger passenger = Passenger.builder()
                    .email(passengerSignupRequestDto.getEmail())
                    .name(passengerSignupRequestDto.getName())
                    .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                    .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword())) //we are encoding pass before saving
                    .build();

           Passenger newPassenger =  passengerRepository.save(passenger);

            return PassengerDto.toDto(newPassenger);
        }
}
