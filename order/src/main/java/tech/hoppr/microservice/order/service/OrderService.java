package tech.hoppr.microservice.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.Order;
import tech.hoppr.microservice.order.published.OrderEvent;
import tech.hoppr.microservice.order.repository.OrderRepository;
import tech.hoppr.microservice.order.shared.MessageEmitter;
import tech.hoppr.microservice.order.model.OrderId;

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
		saveAndPublish(order);
	}

	@Transactional
	public void cancelOrder(OrderId orderId) {
		Instant now = clock.instant();
		Order order = orders.getBy(orderId);
		order.cancel(now);
		saveAndPublish(order);
	}

	private void saveAndPublish(Order order) {
		List<OrderEvent> orderEvents = order.occurredEvents();
		orders.save(order);
		orderEvents.forEach(emitter::emit);
	}
}
