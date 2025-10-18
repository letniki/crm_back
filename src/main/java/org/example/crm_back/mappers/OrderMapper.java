package org.example.crm_back.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.example.crm_back.dto.order.OrderDto;
import org.example.crm_back.dto.order.OrderRequestDto;
import org.example.crm_back.entities.Group;
import org.example.crm_back.entities.Order;
import org.example.crm_back.repositories.GroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final GroupRepository groupRepository;
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
                .groupName(order.getGroup() != null ? order.getGroup().getName() : null)
                .build();
    }

    public void updateEntity(Order order, OrderDto orderDto) {
        if (orderDto == null || order == null) {
            return;
        }
        if (orderDto.getName() != null) order.setName(orderDto.getName());
        if (orderDto.getSurname() != null) order.setSurname(orderDto.getSurname());
        if (orderDto.getEmail() != null) order.setEmail(orderDto.getEmail());
        if (orderDto.getPhone() != null) order.setPhone(orderDto.getPhone());
        if (orderDto.getAge() != null) order.setAge(orderDto.getAge());
        if (orderDto.getCourse() != null) order.setCourse(orderDto.getCourse());
        if (orderDto.getCourseFormat() != null) order.setCourseFormat(orderDto.getCourseFormat());
        if (orderDto.getCourseType() != null) order.setCourseType(orderDto.getCourseType());
        if (orderDto.getSum() != null) order.setSum(orderDto.getSum());
        if (orderDto.getAlreadyPaid() != null) order.setAlreadyPaid(orderDto.getAlreadyPaid());
        if (orderDto.getUtm() != null) order.setUtm(orderDto.getUtm());
        if (orderDto.getMsg() != null) order.setMsg(orderDto.getMsg());
        if (orderDto.getStatus() != null) order.setStatus(orderDto.getStatus());
        if (orderDto.getGroupName() != null) {
            Group group = groupRepository.findByName(orderDto.getGroupName())
                    .orElseGet(() -> new Group(orderDto.getGroupName()));
            order.setGroup(group);
        }
    }

    public OrderDto mapToOrderDto(OrderRequestDto orderRequestDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setName(orderRequestDto.getName());
        orderDto.setSurname(orderRequestDto.getSurname());
        orderDto.setEmail(orderRequestDto.getEmail());
        orderDto.setPhone(orderRequestDto.getPhone());
        orderDto.setAge(NumberUtils.toInt(orderRequestDto.getAge(), 0));
        orderDto.setSum(orderRequestDto.getSum() != null ? orderRequestDto.getSum() : 0);
        orderDto.setAlreadyPaid(orderRequestDto.getAlreadyPaid() != null ? orderRequestDto.getAlreadyPaid() : 0);
        orderDto.setStatus(orderRequestDto.getStatus());
        orderDto.setCourse(orderRequestDto.getCourse());
        orderDto.setCourseFormat(orderRequestDto.getCourseFormat());
        orderDto.setCourseType(orderRequestDto.getCourseType());
        orderDto.setGroupName(orderRequestDto.getGroupName());
        return orderDto;
    }
}
