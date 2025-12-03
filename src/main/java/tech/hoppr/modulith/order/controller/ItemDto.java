package tech.hoppr.modulith.order.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

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
