package tech.hoppr.modulith.inventory.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import tech.hoppr.modulith.inventory.model.ReserveProducts;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.model.OrderPlaced;

@RequiredArgsConstructor
public class OrderPlacedListener {

	private final InventoryService inventoryService;

	@EventListener(OrderPlaced.class)
	public void handle(OrderPlaced event) {
		ReserveProducts reserveProducts = toCommand(event);
		inventoryService.handle(reserveProducts);
	}

	private ReserveProducts toCommand(OrderPlaced event) {
		ReserveProducts.Builder builder = ReserveProducts.builder();
		event.items().forEach(item -> builder.product(item.product(), item.quantity()));
		return builder
			.orderId(event.orderId())
			.build();
	}

}
