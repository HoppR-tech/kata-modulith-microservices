package tech.hoppr.modulith.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
    public void accept(Decrement command) {
		command.productsToDecrement().forEach(productToDecrement -> {
			Product product = inventories.getBy(productToDecrement.ref());
			product.decrement(productToDecrement.quantity());
			inventories.save(product);
		});
    }
}
