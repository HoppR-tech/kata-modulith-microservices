package tech.hoppr.microservice.inventory.repository;

import tech.hoppr.microservice.inventory.model.Product;
import tech.hoppr.microservice.inventory.shared.ProductRef;

public interface InventoryRepository {

	Product getBy(ProductRef ref);

	void save(Product product);
}
