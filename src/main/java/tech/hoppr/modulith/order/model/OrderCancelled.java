package tech.hoppr.modulith.order.model;

import lombok.Builder;
import lombok.NonNull;
import tech.hoppr.modulith.shared.CustomerId;

import java.time.Instant;

@Builder
public record OrderCancelled(
	@NonNull OrderId orderId,
	@NonNull CustomerId customerId,
	@NonNull Instant cancelledAt
) implements OrderEvent {
}
