package com.peb.pebb.appUser;

import java.net.URI;
import java.util.List;

import com.peb.pebb.role.Role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/appusers")
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }

    @PostMapping("/appuser/add")
    public ResponseEntity<AppUser> saveAppUsers(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveAppUsers(appUser));
    }

    /*
     * @PostMapping("/role/add") public ResponseEntity<Role> saveRole(@RequestBody
     * Role role) { URI uri =
     * URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(
     * "/api/role/save").toUriString());
     * 
     * return ResponseEntity.created(uri).body(appUserService.saveRole(role)); }
     * 
     * @PostMapping("/role/addtouser") public ResponseEntity<?>
     * addRoleToAppUser(@RequestBody RoleToAppUserForm form) {
     * appUserService.addRoleToUser(form.getUsername(), form.getAppRole()); return
     * ResponseEntity.ok().build(); }
     */

}

@Data
class RoleToAppUserForm {
    private String username;
    private String appRole;
}