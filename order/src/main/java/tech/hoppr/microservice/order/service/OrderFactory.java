package tech.hoppr.microservice.order.service;

import lombok.RequiredArgsConstructor;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.Order;
import tech.hoppr.microservice.order.model.OrderId;

import java.time.Clock;
import java.util.List;

@RequiredArgsConstructor
public class OrderFactory {

	private final OrderId.Provider idProvider;
	private final Clock clock;

	public Order place(List<Item> items) {
		return Order.place()
			.id(idProvider.get())
			.items(items)
			.requestedAt(clock.instant())
			.build();
	}

}
