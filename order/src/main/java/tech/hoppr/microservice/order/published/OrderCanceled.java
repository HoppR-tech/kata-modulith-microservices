package tech.hoppr.microservice.order.published;

import lombok.Builder;
import tech.hoppr.microservice.order.model.OrderId;

import java.time.Instant;

@Builder
public record OrderCanceled(OrderId orderId, Instant canceledAt) implements OrderEvent {
}
