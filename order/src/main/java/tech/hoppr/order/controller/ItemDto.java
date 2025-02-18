package tech.hoppr.order.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import tech.hoppr.order.model.Item;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

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
