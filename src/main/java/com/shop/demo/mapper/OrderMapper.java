package com.shop.demo.mapper;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.dto.ViewOrderDto;
import com.shop.demo.enitity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "name", target = "orderName")
    @Mapping(source = "price", target = "orderPrice")
    @Mapping(source = "quantity", target = "ordersQuantity")
    Order toOrder(CreateOrderDto orderDto);


    @Mapping(target = "name", source = "orderName")
    @Mapping(target = "price", source = "orderPrice")
    @Mapping(target = "quantity", source = "ordersQuantity")
    ViewOrderDto toViewOrderDto(Order order);

    @Mapping(target = "name", source = "orderName")
    @Mapping(target = "price", source = "orderPrice")
    @Mapping(target = "quantity", source = "ordersQuantity")
    CreateOrderDto toOrderDto(Order order);

}
