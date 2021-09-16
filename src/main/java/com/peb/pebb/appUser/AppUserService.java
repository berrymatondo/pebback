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
        System.out.println("------ appUser ------- " + appUser.getFirstname());

        boolean userExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email déjà utilisé");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        System.out.println("------ encodedPassword ------- " + encodedPassword);

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        System.out.println("------ after save ------- " + appUser.getFirstname());

        addRoleToUser(appUser.getUsername(), "ROLE_USER");
        System.out.println("------ entre roles ------- " + appUser.getFirstname());
        addRoleToUser(appUser.getUsername(), "ROLE_ADMIN");
        // send confirmation token
        System.out.println("------ after roles ------- " + appUser.getFirstname());
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

        System.out.println("------ after in ------- " + username + " " + name);
        AppUser appUser = appUserRepository.findByUsername(username).get();
        System.out.println("------ after in 2 ------- " + username + " " + name);
        Role role = roleRepository.findByName(name);
        System.out.println("------ after in 3 ------- " + username + " " + name);
        appUser.getRoles().add(role);
        System.out.println("------ after in 4 ------- " + username + " " + name);
    }

    public AppUser getAppUser(String username) {
        return appUserRepository.findByUsername(username).get();
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

}
