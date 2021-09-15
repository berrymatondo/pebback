package com.peb.pebb.registration;

import com.peb.pebb.appUser.AppUser;
import com.peb.pebb.appUser.AppUserService;
import com.peb.pebb.jwt.JwtRequest;
import com.peb.pebb.jwt.JwtResponse;
import com.peb.pebb.utility.JWTUtility;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("peb")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private JWTUtility jwtUtility;
    // private AuthenticationManager authenticationManager;
    private DaoAuthenticationProvider daoAuthenticationProvider;
    private AppUserService appUserService;

    @PostMapping("/registration")
    public String register(@RequestBody Registration registration) {
        return registrationService.register(registration);
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        // Authentication
        try {
            daoAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        // Creation jwt token
        final UserDetails userDetails = appUserService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
