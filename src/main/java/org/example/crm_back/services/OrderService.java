package org.example.crm_back.services;

import com.alibaba.excel.EasyExcel;
import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.order.*;
import org.example.crm_back.entities.Group;
import org.example.crm_back.entities.Manager;
import org.example.crm_back.entities.Order;
import org.example.crm_back.mappers.OrderMapper;
import org.example.crm_back.repositories.ManagerRepository;
import org.example.crm_back.repositories.OrderRepository;
import org.example.crm_back.utilities.JwtUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ManagerRepository managerRepository;
    private final JwtUtility jwtUtility;
    private final GroupService groupService;
    private final OrderMapper orderMapper;

    public OrderPaginationResponseDto getOrders(SortDto sortDto) {

        Pageable pageable = createPageable(sortDto.getPage(), sortDto.getOrder(), sortDto.getDirection());
        Page<Order> ordersPage = orderRepository.findAll(pageable);
        return retrieveOrdersFromRepo(pageable, ordersPage);
    }

    public List<StatDTO> getOrderStats() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getStatus() == null ? "Not assigned" : order.getStatus(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> new StatDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    public OrderPaginationResponseDto getOrdersWithFilters(FilterDto filterDto, SortDto sortDto, String token) {
        Pageable pageable = createPageable(sortDto.getPage(), sortDto.getOrder(), sortDto.getDirection());
        String managerSurname = null;
        if (Boolean.TRUE.equals(filterDto.getIsAssignedToMe())) {
            managerSurname = getManagerFromToken(token).getSurname();
        }
        Page<Order> ordersPage = orderRepository.findOrdersFiltered(
                filterDto.getName(),
                filterDto.getSurname(),
                filterDto.getEmail(),
                filterDto.getPhone(),
                filterDto.getStatus(),
                filterDto.getCourse(),
                filterDto.getCourseFormat(),
                filterDto.getCourseType(),
                filterDto.getGroupName(),
                filterDto.getStartDate() != null ? filterDto.getStartDate().atStartOfDay() : null,
                filterDto.getEndDate() != null ? filterDto.getEndDate().atStartOfDay() : null,
                managerSurname,
                pageable
        );
        return retrieveOrdersFromRepo(pageable, ordersPage);
    }

    private OrderPaginationResponseDto retrieveOrdersFromRepo(Pageable pageable, Page<Order> ordersPage) {
        List<OrderDto> orderDtos = ordersPage
                .getContent()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
        Integer nextPage = ordersPage.hasNext() ? pageable.getPageNumber() + 1 : null;
        Integer prevPage = ordersPage.hasPrevious() ? pageable.getPageNumber() - 1 : null;

        return new OrderPaginationResponseDto(
                ordersPage.getTotalElements(),
                pageable.getPageSize(),
                nextPage,
                prevPage,
                orderDtos
        );
    }

    public Pageable createPageable(Integer page, String order, String direction) {


        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(order).ascending()
                : Sort.by(order).descending();
        return PageRequest.of(page - 1, 25, sort);
    }

    @Transactional
    public void updateOrder(Long orderId, OrderDto orderDto, String token) {
        String managerSurname = getManagerFromToken(token).getSurname();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getManager() != null && !order.getManager().equals(managerSurname)) {
            throw new RuntimeException("You can only update your orders");
        }

        orderMapper.updateEntity(order, orderDto);

        if (orderDto.getGroupName() != null) {
            Group group = groupService.getOrCreateGroup(orderDto.getGroupName().toUpperCase());
            order.setGroup(group);
        }

        orderRepository.save(order);
    }

    public byte[] exportToExcel(FilterDto filterDto, String token) {
        String managerSurname = null;

        if (Boolean.TRUE.equals(filterDto.getIsAssignedToMe())) {
            managerSurname = getManagerFromToken(token).getSurname();
        }

        List<Order> orders = orderRepository.findOrdersFiltered(
                filterDto.getName(),
                filterDto.getSurname(),
                filterDto.getEmail(),
                filterDto.getPhone(),
                filterDto.getStatus(),
                filterDto.getCourse(),
                filterDto.getCourseFormat(),
                filterDto.getCourseType(),
                filterDto.getGroupName(),
                filterDto.getStartDate() != null ? filterDto.getStartDate().atStartOfDay() : null,
                filterDto.getEndDate() != null ? filterDto.getEndDate().atStartOfDay() : null,
                managerSurname
        );

        List<OrderDto> orderDtos = orders.stream()
                .map(OrderMapper::toDto)
                .toList();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        EasyExcel.write(outputStream, OrderDto.class)
                .sheet("orders " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy")))
                .doWrite(orderDtos);

        return outputStream.toByteArray();
    }

    private Manager getManagerFromToken(String token) {
        String email = jwtUtility.extractUsername(token);
        return managerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
    }
}
