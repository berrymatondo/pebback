package com.peb.pebb.registration;

import com.peb.pebb.appUser.AppUser;
import com.peb.pebb.appUser.AppUserRole;
import com.peb.pebb.appUser.AppUserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;;
    private final EmailValidator emailValidator;

    public String register(Registration registration) {
        boolean isValidEmail = emailValidator.test(registration.getUsername());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        /*
         * return appUserService.signUpUser(new AppUser(registration.getFirstname(),
         * registration.getLastname(), registration.getUsername(),
         * registration.getPassword(), AppUserRole.USER));
         */
        return appUserService.signUpUser(new AppUser(registration.getFirstname(), registration.getLastname(),
                registration.getUsername(), registration.getPassword()));
    }

}
