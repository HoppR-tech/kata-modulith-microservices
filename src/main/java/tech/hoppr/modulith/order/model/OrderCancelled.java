package tech.hoppr.modulith.order.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderCancelled(OrderId orderId, Instant cancelledAt) implements OrderEvent {
}
