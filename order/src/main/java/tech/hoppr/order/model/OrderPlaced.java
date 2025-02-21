package tech.hoppr.order.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import tech.hoppr.shared.CustomerId;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderPlaced(
	@NonNull OrderId orderId,
	@NonNull CustomerId customerId,
	@Singular("item") List<Item> items,
	@NonNull Instant placedAt
) implements OrderEvent {

	@Builder
	public record Item(ProductRef productRef, Quantity quantity) {}

}
