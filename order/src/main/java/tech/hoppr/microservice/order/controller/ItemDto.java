package tech.hoppr.microservice.order.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.ProductRef;
import tech.hoppr.microservice.order.model.Quantity;

public record ItemDto(
	@NotBlank String productRef,
	@Positive Integer quantity
) {
	public Item toDomain() {
		return Item.builder()
			.product(ProductRef.of(productRef))
			.quantity(Quantity.of(quantity))
			.build();
	}
}
