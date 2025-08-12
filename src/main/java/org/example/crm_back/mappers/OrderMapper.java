package org.example.crm_back.mappers;

import org.example.crm_back.dto.order.OrderDto;
import org.example.crm_back.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public static OrderDto toDto(Order order) {
        if (order == null) return null;
        return OrderDto.builder()
                .id(order.getId())
                .name(order.getName())
                .surname(order.getSurname())
                .email(order.getEmail())
                .phone(order.getPhone())
                .age(order.getAge())
                .course(order.getCourse())
                .courseFormat(order.getCourseFormat())
                .courseType(order.getCourseType())
                .sum(order.getSum())
                .alreadyPaid(order.getAlreadyPaid())
                .createdAt(order.getCreatedAt())
                .utm(order.getUtm())
                .msg(order.getMsg())
                .status(order.getStatus())
                .manager(order.getManager())
                .group(order.getGroup())
                .build();
    }

    public static Order toEntity(OrderDto orderDto) {
        if (orderDto == null) return null;

        return Order.builder().id(orderDto.getId())
                .name(orderDto.getName())
                .surname(orderDto.getSurname())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .age(orderDto.getAge())
                .course(orderDto.getCourse())
                .courseFormat(orderDto.getCourseFormat())
                .courseType(orderDto.getCourseType())
                .sum(orderDto.getSum())
                .alreadyPaid(orderDto.getAlreadyPaid())
                .createdAt(orderDto.getCreatedAt())
                .utm(orderDto.getUtm())
                .msg(orderDto.getMsg())
                .status(orderDto.getStatus())
                .manager(orderDto.getManager())
                .group(orderDto.getGroup())
                .build();
    }
}
