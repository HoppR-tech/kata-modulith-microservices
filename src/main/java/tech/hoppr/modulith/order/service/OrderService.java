package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.published.OrderCanceled;
import tech.hoppr.modulith.order.published.OrderPlaced;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.shared.MessageEmitter;
import tech.hoppr.modulith.shared.OrderId;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OrderService {

	private final OrderFactory factory;
	private final OrderRepository orders;
	private final Clock clock;
	private final MessageEmitter emitter;

	@Transactional
	public void placeOrder(List<Item> items) {
		Order order = factory.place(items);
		orders.save(order);

		emitter.emit(OrderPlaced.builder()
			.orderId(order.id())
			.items(items.stream()
				.map(item -> OrderPlaced.Item.builder()
					.productRef(item.product())
					.quantity(item.quantity())
					.build())
				.toList())
			.build());
	}

	@Transactional
	public void cancelOrder(OrderId orderId) {
		Instant now = clock.instant();
		Order order = orders.getBy(orderId);
		order.cancel(now);
		orders.save(order);

		emitter.emit(OrderCanceled.builder()
			.orderId(orderId)
			.build());
	}
}
