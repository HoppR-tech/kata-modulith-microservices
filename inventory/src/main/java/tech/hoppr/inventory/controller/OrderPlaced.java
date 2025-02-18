package tech.hoppr.inventory.controller;

import lombok.Builder;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, List<Item> items) {

	public record OrderId(String value) {}

	@Builder
	public record Item(ProductRef productRef, Quantity quantity) {}

}
