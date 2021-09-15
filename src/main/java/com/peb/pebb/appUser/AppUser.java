package com.peb.pebb.appUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.peb.pebb.role.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "appusers")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) // quand on load le user,on load aussi ses roles
    private Collection<Role> roles = new ArrayList<>();
    /*
     * @Enumerated(EnumType.STRING) private AppUserRole appUserRole; private boolean
     * locked = false; private boolean enabled = true;
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        /*
         * SimpleGrantedAuthority authority = new
         * SimpleGrantedAuthority(appUserRole.name()); return
         * Collections.singletonList(authority);
         */
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            System.out.println("role:=" + role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return authorities;
    }

    /*
     * public AppUser(String firstname, String lastname, String username, String
     * password, AppUserRole appUserRole) { this.firstname = firstname;
     * this.lastname = lastname; this.username = username; this.password = password;
     * this.appUserRole = appUserRole;
     * 
     * }
     */

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    public AppUser(String firstname, String lastname, String username, String password, Collection<Role> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public AppUser(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;

    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        // return !locked;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        // return enabled;
        return true;
    }

}
