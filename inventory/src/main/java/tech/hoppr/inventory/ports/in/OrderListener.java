package tech.hoppr.inventory.ports.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import tech.hoppr.inventory.model.CancelReservation;
import tech.hoppr.inventory.model.OrderId;
import tech.hoppr.inventory.model.ReserveProducts;
import tech.hoppr.inventory.service.InventoryService;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

@Slf4j
@RequiredArgsConstructor
public class OrderListener {

	private final InventoryService inventoryService;

	@RabbitListener(queues = "order.cancelled")
	public void handle(OrderCancelled event) {
		inventoryService.handle(CancelReservation.builder()
			.orderId(OrderId.of(event.orderId()))
			.build());
	}

	@RabbitListener(queues = "order.placed")
	public void handle(OrderPlaced event) {
		ReserveProducts reserveProducts = toCommand(event);
		inventoryService.handle(reserveProducts);
	}

	private ReserveProducts toCommand(OrderPlaced event) {
		ReserveProducts.Builder builder = ReserveProducts.builder();

		event.items().forEach(item -> builder.product(
			ProductRef.of(item.productRef()),
			Quantity.of(item.quantity())));

		return builder
			.orderId(OrderId.of(event.orderId()))
			.build();
	}

}
