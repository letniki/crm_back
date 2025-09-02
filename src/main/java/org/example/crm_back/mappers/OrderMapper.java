package org.example.crm_back.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.example.crm_back.dto.order.OrderDto;
import org.example.crm_back.dto.order.OrderFormDataDto;
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

    public void updateEntity(Order order, OrderDto dto) {
        if (dto == null || order == null) {
            return;
        }

        order.setName(dto.getName());
        order.setSurname(dto.getSurname());
        order.setEmail(dto.getEmail());
        order.setPhone(dto.getPhone());
        order.setAge(dto.getAge());
        order.setCourse(dto.getCourse());
        order.setCourseFormat(dto.getCourseFormat());
        order.setCourseType(dto.getCourseType());
        order.setSum(dto.getSum());
        order.setAlreadyPaid(dto.getAlreadyPaid());
        order.setUtm(dto.getUtm());
        order.setMsg(dto.getMsg());
        order.setStatus(dto.getStatus());

        if (dto.getGroupName() != null) {
            Group group = groupRepository.findByName(dto.getGroupName())
                    .orElseGet(() -> new Group(dto.getGroupName()));
            order.setGroup(group);
        }
    }

    public OrderDto mapToOrderDto(OrderFormDataDto orderFormDataDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setName(orderFormDataDto.getName());
        orderDto.setSurname(orderFormDataDto.getSurname());
        orderDto.setEmail(orderFormDataDto.getEmail());
        orderDto.setPhone(orderFormDataDto.getPhone());
        orderDto.setAge(NumberUtils.toInt(orderFormDataDto.getAge(), 0));
        orderDto.setSum(orderFormDataDto.getSum() != null ? orderFormDataDto.getSum().intValue() : 0);
        orderDto.setAlreadyPaid(orderFormDataDto.getAlreadyPaid() != null ? orderFormDataDto.getAlreadyPaid().intValue() : 0);
        orderDto.setStatus(orderFormDataDto.getStatus());
        orderDto.setCourse(orderFormDataDto.getCourse());
        orderDto.setCourseFormat(orderFormDataDto.getCourseFormat());
        orderDto.setCourseType(orderFormDataDto.getCourseType());
        orderDto.setGroupName(orderFormDataDto.getGroupName());

        return orderDto;
    }
}
