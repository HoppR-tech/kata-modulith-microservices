package tech.hoppr.microservice.order.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import tech.hoppr.microservice.order.model.Item;

import java.util.List;

public record PlaceOrderDto(@Valid @NotEmpty List<ItemDto> items) {

	public List<Item> toDomain() {
		return items.stream()
			.map(ItemDto::toDomain)
			.toList();
	}

}
