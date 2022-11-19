package com.shop.demo.auth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String username;
    private String email;
    private Set<String> roles;
    private String password;


}
