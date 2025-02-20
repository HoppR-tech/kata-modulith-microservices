package tech.hoppr.modulith.inventory.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import tech.hoppr.modulith.inventory.model.CancelReservation;
import tech.hoppr.modulith.inventory.model.ReserveProducts;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.model.OrderCancelled;
import tech.hoppr.modulith.order.model.OrderPlaced;

@RequiredArgsConstructor
public class OrderCancelledListener {

	private final InventoryService inventoryService;

	@EventListener(OrderCancelled.class)
	public void handle(OrderCancelled event) {
		inventoryService.handle(CancelReservation.builder()
			.orderId(event.orderId())
			.build());
	}

}
