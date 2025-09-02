package org.example.crm_back.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.crm_back.dto.order.*;
import org.example.crm_back.mappers.OrderMapper;
import org.example.crm_back.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/")
    public ResponseEntity<OrderPaginationResponseDto> getOrders(
            @Valid SortDto sortDto,
            @Valid FilterDto filterDto) {
        if (filterDto == null || filterDto.isEmpty()) {
            OrderPaginationResponseDto response = orderService.getOrders(sortDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            OrderPaginationResponseDto response = orderService.getOrdersWithFilters(filterDto, sortDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id,
                                            @RequestBody OrderFormDataDto orderFormDataDto,
                                            @RequestHeader("Authorization") String token){
        OrderDto orderDto = orderMapper.mapToOrderDto(orderFormDataDto);
        orderService.updateOrder(id, orderDto, token.replace("Bearer ", ""));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
