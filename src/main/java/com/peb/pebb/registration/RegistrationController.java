package com.peb.pebb.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.peb.pebb.appUser.AppUser;
import com.peb.pebb.appUser.AppUserRepository;
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
    private AppUserRepository appUserRepository;

    @PostMapping("/registration")
    public String register(@RequestBody Registration registration) {
        System.out.println("===================Register=================");
        return registrationService.register(registration);
    }

    @PostMapping("/authenticate")

    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        boolean test = appUserRepository.findByUsername(jwtRequest.getUsername()).isPresent();
        if (!test) {
            throw new Exception("Cet utilisateur n'est pas connu");
        }

        // Authentication
        try {
            daoAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        // Creation jwt token
        /*
         * final UserDetails userDetails =
         * appUserService.loadUserByUsername(jwtRequest.getUsername());
         * 
         * final String token = jwtUtility.generateToken(userDetails);
         * 
         * return new JwtResponse(token);
         */
        final UserDetails userDetails = appUserService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtUtility.generateToken(userDetails);

        List<String> claims2 = new ArrayList<>();
        claims2 = userDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList());

        String firstname = appUserRepository.findByUsername(jwtRequest.getUsername()).get().getFirstname();
        Long appUserId = appUserRepository.findByUsername(jwtRequest.getUsername()).get().getId();

        return new JwtResponse(token, claims2, firstname, appUserId);
    }
}
