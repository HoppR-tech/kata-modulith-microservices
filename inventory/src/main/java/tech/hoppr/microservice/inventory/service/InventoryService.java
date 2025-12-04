package tech.hoppr.microservice.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.microservice.inventory.model.Product;
import tech.hoppr.microservice.inventory.repository.InventoryRepository;
import tech.hoppr.microservice.inventory.service.Decrement;

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
