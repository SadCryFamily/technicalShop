package com.shop.demo.mapper;

import com.shop.demo.dto.CreateCustomerDto;
import com.shop.demo.enitity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "name", target = "customerName")
    @Mapping(source = "email", target = "customerEmail")
    @Mapping(source = "password", target = "customerPassword")
    Customer toCustomer(CreateCustomerDto customerDto);

}
