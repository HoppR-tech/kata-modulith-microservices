package tech.hoppr.modulith.repository.inventory;

import tech.hoppr.modulith.model.Product;
import tech.hoppr.modulith.model.ProductRef;

public interface InventoryRepository {

	Product getBy(ProductRef ref);

	void save(Product product);

}
