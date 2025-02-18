package tech.hoppr.inventory.repository;

import tech.hoppr.inventory.model.Product;
import tech.hoppr.shared.ProductRef;

public interface InventoryRepository {

	Product getBy(ProductRef ref);

	void save(Product product);

}
