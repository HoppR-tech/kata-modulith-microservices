package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.model.OrderId;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class OrderFactory {

	private final OrderId.Provider idProvider;
	private final Clock clock;

	public Order create(List<Item> items) {
		return Order.place()
			.id(idProvider.get())
			.items(items)
			.placedAt(Instant.now(clock))
			.build();
	}

}
