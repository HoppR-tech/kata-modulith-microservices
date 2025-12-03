package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.published.OrderPlaced;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.shared.MessageEmitter;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {

	private final OrderFactory factory;
	private final OrderRepository orders;
	private final MessageEmitter emitter;

	@Transactional
	public void placeOrder(List<Item> items) {
		Order order = factory.create(items);
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

}
