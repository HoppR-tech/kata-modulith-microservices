package tech.hoppr.modulith.loyalty.model;

import lombok.Builder;
import lombok.NonNull;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.shared.CustomerId;

@Builder
public record LoyaltyPointsAdded(
	@NonNull CustomerId customerId,
	@NonNull OrderId orderId,
	int addedPoints
) implements LoyaltyEvent {
}
