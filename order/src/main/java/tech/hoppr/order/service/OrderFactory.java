package tech.hoppr.order.service;

import lombok.RequiredArgsConstructor;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderId;

import java.util.List;

@RequiredArgsConstructor
public class OrderFactory {

	private final OrderId.Provider idProvider;

	public Order create(List<Item> items) {
		return Order.builder()
			.id(idProvider.get())
			.items(items)
			.build();
	}

}
