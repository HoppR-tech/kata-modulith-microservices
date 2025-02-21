package tech.hoppr.listener;

import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;
import tech.hoppr.model.CancelReservation;
import tech.hoppr.service.InventoryService;
import tech.hoppr.shared.OrderCancelled;

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
