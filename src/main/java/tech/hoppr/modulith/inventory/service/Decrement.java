package tech.hoppr.modulith.inventory.service;

import lombok.Builder;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

@Builder
public record Decrement(List<ProductToDecrement> productsToDecrement) {

	@Builder
	public record ProductToDecrement(ProductRef ref, Quantity quantity) {}

}
