package tech.hoppr.order.port.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import tech.hoppr.order.model.Item;

import java.util.List;

public record PlaceOrderRequest(@NotNull String customerId, @Valid @NotEmpty List<ItemDto> items) {

	public List<Item> toDomain() {
		return items.stream()
			.map(ItemDto::toDomain)
			.toList();
	}

}
