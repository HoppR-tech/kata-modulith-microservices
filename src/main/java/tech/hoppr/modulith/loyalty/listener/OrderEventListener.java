package tech.hoppr.modulith.loyalty.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import tech.hoppr.modulith.loyalty.service.LoyaltyService;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.OrderCancelled;
import tech.hoppr.modulith.order.model.OrderPlaced;
import tech.hoppr.modulith.shared.Quantity;

@RequiredArgsConstructor
public class OrderEventListener {

	private final LoyaltyService loyaltyService;

	@EventListener
	public void handle(OrderCancelled event) {
		loyaltyService.cancelPoints(event.customerId(), event.orderId());
	}

	@EventListener
	public void handle(OrderPlaced event) {
		Quantity quantity = event.items().stream()
			.map(Item::quantity)
			.reduce(Quantity.ZERO, Quantity::sum);

		loyaltyService.addPoints(event.customerId(), event.orderId(), quantity.value());
	}

}
