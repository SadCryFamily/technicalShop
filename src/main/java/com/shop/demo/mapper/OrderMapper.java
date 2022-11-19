package com.shop.demo.mapper;

import com.shop.demo.dto.CreateOrderDto;
import com.shop.demo.enitity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "name", target = "orderName")
    @Mapping(source = "quantity", target = "ordersQuantity")
    Order toOrder(CreateOrderDto orderDto);

}
