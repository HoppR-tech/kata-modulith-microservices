package tech.hoppr.modulith.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import tech.hoppr.modulith.inventory.service.Decrement;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.published.OrderPlaced;

@RequiredArgsConstructor
public class OnOrderPlacedListener {

	private final InventoryService inventoryService;

	@EventListener
	public void handle(OrderPlaced event) {
		inventoryService.accept(Decrement.builder()
				.productsToDecrement(event.items().stream()
					.map(item -> Decrement.ProductToDecrement.builder()
						.ref(item.productRef())
						.quantity(item.quantity())
						.build())
					.toList())
			.build());
	}

}
