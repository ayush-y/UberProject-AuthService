package org.example.uberprojectauthservice.dto;

import lombok.*;
import org.example.uberprojectentityservice.models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {

    private String id;

    private String name;

    private String email;

    private String password; //encrypted password

    private String phoneNumber;

    private Date createdAt;

    public static PassengerDto toDto(Passenger p) {
        return PassengerDto.builder().
                id(p.getId().toString())
                .name(p.getName())
                .email(p.getEmail())
                .password(p.getPassword()) //TODO: Encrypt the password
                .phoneNumber(p.getPhoneNumber())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
