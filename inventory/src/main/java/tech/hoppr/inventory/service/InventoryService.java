package tech.hoppr.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.inventory.model.Product;
import tech.hoppr.inventory.model.Products;
import tech.hoppr.inventory.repository.InventoryRepository;

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
