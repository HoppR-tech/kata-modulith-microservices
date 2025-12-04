package tech.hoppr.microservice.inventory.service;

import lombok.Builder;
import tech.hoppr.microservice.inventory.model.ProductRef;
import tech.hoppr.microservice.inventory.model.Quantity;

import java.util.List;

@Builder
public record Decrement(List<ProductToDecrement> productsToDecrement) {

	@Builder
	public record ProductToDecrement(ProductRef ref, Quantity quantity) {}

}
