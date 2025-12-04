package tech.hoppr.microservice.order.spi.inventory.on_order_placed;

import lombok.Builder;
import tech.hoppr.microservice.order.spi.inventory.InventoryClient;

import java.util.List;

@Builder
public record OrderPlacedDto(String orderId, List<ItemDto> items) {

	@Builder
	public record ItemDto(String productRef, int quantity) {
	}

}
