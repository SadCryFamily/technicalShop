package com.shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDto {

    @NotNull
    @Size(min = 10, message = "Name is too small.")
    private String name;

    @Email
    @NotNull
    @Size(min = 10, message = "Email is too small.")
    private String email;

}
