package tech.hoppr.modulith.order.controller;

import java.time.LocalDateTime;

public record OrderDto(String orderId, LocalDateTime placedAt, LocalDateTime canceledAt) {
}
