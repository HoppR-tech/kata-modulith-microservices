package tech.hoppr.modulith.order.published;

import lombok.Builder;
import tech.hoppr.modulith.shared.OrderId;

import java.time.Instant;

@Builder
public record OrderCanceled(OrderId orderId, Instant canceledAt) implements OrderEvent {
}
