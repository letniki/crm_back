package org.example.crm_back.services;

import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.order.OrderDto;
import org.example.crm_back.dto.order.OrderPaginationResponseDto;
import org.example.crm_back.entities.Order;
import org.example.crm_back.mappers.OrderMapper;
import org.example.crm_back.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderPaginationResponseDto getOrders(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        List<OrderDto> orderDtos = page.stream()
         .map(OrderMapper::toDto)
                .collect(Collectors.toList());

        return new OrderPaginationResponseDto(
                page.getTotalElements(),
                page.getSize(),
                page.hasNext() ? page.getNumber() + 1 : null,
                page.hasPrevious() ? page.getNumber() - 1 : null,
                orderDtos
        );
    }
    public Pageable createPageable(int page, String sortBy, String direction) {
        int pageNumber = page - 1;
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Order.asc(sortBy))
                : Sort.by(Sort.Order.desc(sortBy));

        return PageRequest.of(pageNumber, 25, sort);

    }
}
