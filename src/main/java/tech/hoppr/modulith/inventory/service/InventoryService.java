package tech.hoppr.modulith.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.order.model.OrderPlaced;

import java.util.List;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
	@EventListener(OrderPlaced.class)
    public void handle(OrderPlaced event) {
		List<OrderPlaced.Item> items = event.items();

		items.forEach(item -> {
			Product product = inventories.getBy(item.productRef());
			product.decrement(item.quantity());
			inventories.save(product);
		});
    }
}
