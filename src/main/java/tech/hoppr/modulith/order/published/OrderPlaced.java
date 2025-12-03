package tech.hoppr.modulith.order.published;

import lombok.Builder;
import tech.hoppr.modulith.shared.OrderId;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, List<Item> items) {

	@Builder
	public record Item(ProductRef productRef, Quantity quantity) {}

}
