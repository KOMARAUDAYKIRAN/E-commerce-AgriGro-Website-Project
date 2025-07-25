package com.agriBazaar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderSummaryResponse {
    private Long orderId;
    private Long userId;
    private List<Long> productIds;
}
