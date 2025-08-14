package com.watches.backend.Dto.OrderDto;

import com.watches.backend.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private List<OrderItemDTO> items;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;
}
