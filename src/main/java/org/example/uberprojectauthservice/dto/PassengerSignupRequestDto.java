package org.example.uberprojectauthservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerSignupRequestDto {

    String name;

    String email;

    String password;

    String phoneNumber;


}
