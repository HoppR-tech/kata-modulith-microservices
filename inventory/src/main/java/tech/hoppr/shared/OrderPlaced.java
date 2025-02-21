package tech.hoppr.modulith.order.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import tech.hoppr.modulith.shared.CustomerId;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderPlaced(
	@NonNull OrderId orderId,
	@NonNull CustomerId customerId,
	@Singular("item") List<Item> items,
	@NonNull Instant placedAt
) implements OrderEvent {
}
