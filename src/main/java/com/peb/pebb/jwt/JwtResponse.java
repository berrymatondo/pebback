package com.peb.pebb.jwt;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;
    private List<String> roles = new ArrayList<>();
    private String firstname;
    private Long appUserId;
}
