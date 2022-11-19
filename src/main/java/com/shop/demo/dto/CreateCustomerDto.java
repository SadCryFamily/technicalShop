package com.shop.demo.dto;

import com.shop.demo.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDto {

    @NotNull
    @Size(min = 10, message = "Name is too small.")
    private String name;

    @NotNull
    @Size(min = 8, message = "Password is too small.")
    private String password;

    @Email
    @NotNull
    @Size(min = 10, message = "Email is too small.")
    private String email;

    private Set<String> roles;

}
