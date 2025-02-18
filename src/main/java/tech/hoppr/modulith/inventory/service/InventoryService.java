package tech.hoppr.modulith.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.model.Products;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.order.model.OrderPlaced;

import java.util.List;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
    public void decrement(Products products) {
		products.forEach((ref, quantity) -> {
			Product product = inventories.getBy(ref);
			product.decrement(quantity);
			inventories.save(product);
		});
    }
}
