package tech.hoppr.modulith.service;

import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Order;
import tech.hoppr.modulith.model.OrderId;

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
