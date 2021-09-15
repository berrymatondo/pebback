package com.peb.pebb.appUser;

import java.util.List;

import javax.transaction.Transactional;

import com.peb.pebb.role.Role;
import com.peb.pebb.role.RoleRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor // Add constructor
@Transactional
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "Userwith email %s not found";
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email déjà utilisé");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        addRoleToUser(appUser.getUsername(), "ROLE_USER");
        addRoleToUser(appUser.getUsername(), "ROLE_ADMIN");
        // send confirmation token

        return "it works";
    }

    public AppUser saveAppUsers(AppUser appUser) {
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        return appUserRepository.save(appUser);
    }

    public Role saveRole(Role role) {

        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String name) {
        AppUser appUser = appUserRepository.findByUsername(username).get();
        Role role = roleRepository.findByName(name);

        appUser.getRoles().add(role);
    }

    public AppUser getAppUser(String username) {
        return appUserRepository.findByUsername(username).get();
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

}
