package tech.hoppr.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderPlaced;
import tech.hoppr.order.repository.OrderRepository;
import tech.hoppr.shared.MessageBus;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {

	private final OrderFactory factory;
    private final OrderRepository orders;
    private final MessageBus messageBus;

    @Transactional
    public void placeOrder(List<Item> items) {
		Order order = factory.create(items);
        orders.save(order);

        messageBus.emit(OrderPlaced.builder()
			.orderId(order.id())
			.items(toItems(order))
			.build());
    }

	private static List<OrderPlaced.Item> toItems(Order order) {
		return order.items().stream()
			.map(item -> OrderPlaced.Item.builder()
				.productRef(item.product())
				.quantity(item.quantity())
				.build())
			.toList();
	}

}
