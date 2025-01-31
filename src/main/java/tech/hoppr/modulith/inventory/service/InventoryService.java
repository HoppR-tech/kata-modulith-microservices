package tech.hoppr.modulith.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;

import java.util.List;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
    public void decrement(List<Item> items) {
		items.forEach(item -> {
			Product product = inventories.getBy(item.product());
			product.decrement(item.quantity());
			inventories.save(product);
		});
    }
}
