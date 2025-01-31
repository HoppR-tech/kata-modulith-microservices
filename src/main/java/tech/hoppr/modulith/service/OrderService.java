package tech.hoppr.modulith.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Order;
import tech.hoppr.modulith.repository.order.OrderRepository;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {

	private final OrderFactory factory;
    private final OrderRepository orders;
    private final InventoryService inventoryService;

    @Transactional
    public void placeOrder(List<Item> items) {
		Order order = factory.create(items);
        orders.save(order);

        inventoryService.decrement(items);
    }

}
