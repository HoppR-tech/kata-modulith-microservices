package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.shared.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.model.OrderId;

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
