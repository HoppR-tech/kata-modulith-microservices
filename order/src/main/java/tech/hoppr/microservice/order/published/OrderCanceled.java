package tech.hoppr.microservice.order.published;

import lombok.Builder;
import tech.hoppr.microservice.order.published.OrderEvent;
import tech.hoppr.microservice.order.shared.OrderId;

import java.time.Instant;

@Builder
public record OrderCanceled(OrderId orderId, Instant canceledAt) implements OrderEvent {
}
