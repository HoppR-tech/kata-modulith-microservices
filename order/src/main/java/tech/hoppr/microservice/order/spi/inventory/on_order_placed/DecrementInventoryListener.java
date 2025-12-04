package tech.hoppr.microservice.order.spi.inventory.on_order_placed;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import tech.hoppr.microservice.order.published.OrderPlaced;
import tech.hoppr.microservice.order.spi.inventory.InventoryClient;

@RequiredArgsConstructor
public class DecrementInventoryListener {

	private final InventoryClient inventoryClient;

	@EventListener
	public void handle(OrderPlaced event) {
		inventoryClient.accept(OrderPlacedDto.builder()
			.orderId(event.orderId().value())
			.items(event.items().stream()
				.map(item -> OrderPlacedDto.ItemDto.builder()
					.productRef(item.productRef().value())
					.quantity(item.quantity().value())
					.build())
				.toList())
			.build());
	}

}
