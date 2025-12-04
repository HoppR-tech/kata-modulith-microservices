package tech.hoppr.microservice.order.published;

import lombok.Builder;
import tech.hoppr.microservice.order.shared.OrderId;
import tech.hoppr.microservice.order.shared.ProductRef;
import tech.hoppr.microservice.order.shared.Quantity;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, List<Item> items, Instant placedAt) implements OrderEvent {

	@Builder
	public record Item(ProductRef productRef, Quantity quantity) {}

}
