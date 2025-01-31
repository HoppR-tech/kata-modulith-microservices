package tech.hoppr.modulith.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;

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
