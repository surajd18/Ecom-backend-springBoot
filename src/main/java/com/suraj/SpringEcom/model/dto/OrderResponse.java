package com.suraj.SpringEcom.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        String customerName,
        String email,
        String status,
        LocalDate orderDate,
        List<OrderItemResponse> items
) {
}
