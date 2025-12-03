package tech.hoppr.modulith.inventory.repository;

import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.model.ProductRef;

public interface InventoryRepository {

	Product getBy(ProductRef ref);

	void save(Product product);
}
