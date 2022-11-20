package com.shop.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateOrderDto {

    @NotNull
    @Size(min = 8, message = "Order name too small.")
    private String name;

    @NotNull
    private Long quantity;

    @NotNull
    private Long price;

    @NotNull
    private boolean isPayed;

}
