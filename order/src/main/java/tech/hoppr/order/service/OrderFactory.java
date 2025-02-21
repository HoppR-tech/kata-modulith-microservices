package tech.hoppr.order.service;

import lombok.RequiredArgsConstructor;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.shared.CustomerId;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OrderFactory {

	private final OrderId.Provider idProvider;
	private final Clock clock;

	public Order create(CustomerId customerId, List<Item> items) {
		return Order.place()
			.id(idProvider.get())
			.customerId(customerId)
			.items(items)
			.placedAt(Instant.now(clock))
			.build();
	}

}
