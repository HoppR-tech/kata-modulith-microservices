package tech.hoppr.microservice.order.controller;

import java.time.LocalDateTime;

public record OrderDto(String orderId, LocalDateTime placedAt, LocalDateTime canceledAt) {
}
