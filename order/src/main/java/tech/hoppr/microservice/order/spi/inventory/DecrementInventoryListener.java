package tech.hoppr.microservice.order.spi.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import tech.hoppr.microservice.order.published.OrderPlaced;

@RequiredArgsConstructor
public class DecrementInventoryListener {

	private final InventoryClient inventoryClient;

	@EventListener
	public void handle(OrderPlaced event) {
	}

}
