package org.example.uberprojectauthservice.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.uberprojectauthservice.Services.AuthService;
import org.example.uberprojectauthservice.Services.JwtService;
import org.example.uberprojectauthservice.dto.AuthRequestDto;
import org.example.uberprojectauthservice.dto.AuthResponseDto;
import org.example.uberprojectauthservice.dto.PassengerDto;
import org.example.uberprojectauthservice.dto.PassengerSignupRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    private final JwtService jwtService;

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto  passengerSignupRequestDto) {

        PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        System.out.println("DTO: " + authRequestDto);

        System.out.println("Request is coming for signing " +authRequestDto.getEmail());
        System.out.println("Request is coming for signing "+ authRequestDto.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail()
                        , authRequestDto.getPassword()));
        if((authentication.isAuthenticated())) {
            String jwtToken = jwtService.createToken(authRequestDto.getEmail());

            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                            .httpOnly(false)
                            .secure(false)
                            .path("/")
                            .maxAge(7*24*60*60)
                            .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("failed auth",HttpStatus.UNAUTHORIZED);
        }

    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken( HttpServletRequest request, HttpServletResponse response) {
        for(Cookie cookie :  request.getCookies()) {
            if(cookie.getName().equals("JwtToken")) {
                System.out.println(cookie.getName()+ " "+cookie.getValue());
            }
        }
            return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
