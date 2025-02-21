package tech.hoppr.modulith.order.listener;

import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.infra.adapter.InventoryClientAdapter;
import tech.hoppr.modulith.order.model.OrderPlaced;

@RequiredArgsConstructor
public class OrderPlacedListener {

	private final InventoryClientAdapter inventoryClientAdapter;

	@EventListener(OrderPlaced.class)
	public void handle(OrderPlaced event) {
		inventoryClientAdapter.ReverseProduct(event);
	}

}
