package tech.hoppr.modulith.order.model;

import lombok.Builder;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, List<OrderPlaced.Item> items) {

	@Builder
	public record Item(ProductRef productRef, Quantity quantity) {}

}
