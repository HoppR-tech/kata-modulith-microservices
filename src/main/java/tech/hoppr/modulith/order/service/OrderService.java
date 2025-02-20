package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.shared.MessageBus;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OrderService {

	private final OrderFactory factory;
	private final OrderRepository orders;
	private final MessageBus messageBus;
	private final Clock clock;

	@Transactional
	public OrderId placeOrder(List<Item> items) {
		Order order = factory.create(items);
		saveAndEmit(order);
		return order.id();
	}

	@Transactional
	public void cancel(OrderId orderId) {
		Order order = orders.getBy(orderId);
		order.cancel(Instant.now(clock));
		saveAndEmit(order);
	}

	private void saveAndEmit(Order order) {
		orders.save(order);
		emitEvents(order);
	}

	private void emitEvents(Order order) {
		order.occurredEvents().forEach(messageBus::emit);
	}
}
