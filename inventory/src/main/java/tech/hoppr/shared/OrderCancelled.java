package tech.hoppr.shared;

import lombok.Builder;
import lombok.NonNull;
import tech.hoppr.shared.CustomerId;

import java.time.Instant;

@Builder
public record OrderCancelled(
	@NonNull OrderId orderId,
	@NonNull CustomerId customerId,
	@NonNull Instant cancelledAt
) implements OrderEvent {
}
