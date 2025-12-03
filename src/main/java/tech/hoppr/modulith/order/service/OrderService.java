package tech.hoppr.modulith.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.shared.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.repository.OrderRepository;

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
