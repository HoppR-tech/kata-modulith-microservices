package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.model.OrderPlaced;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.shared.MessageBus;

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
			.items(order.items())
			.build());
    }

}
