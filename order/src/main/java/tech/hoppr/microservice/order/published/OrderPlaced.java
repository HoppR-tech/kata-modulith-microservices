package tech.hoppr.microservice.order.published;

import lombok.Builder;
import tech.hoppr.microservice.order.model.OrderId;
import tech.hoppr.microservice.order.model.ProductRef;
import tech.hoppr.microservice.order.model.Quantity;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, List<Item> items, Instant placedAt) implements OrderEvent {

	@Builder
	public record Item(ProductRef productRef, Quantity quantity) {}

}
