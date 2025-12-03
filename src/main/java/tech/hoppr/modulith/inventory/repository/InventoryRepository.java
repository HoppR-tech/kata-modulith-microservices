package tech.hoppr.modulith.inventory.repository;

import tech.hoppr.modulith.inventory.entity.InventoryEntity;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.model.ProductRef;

import java.util.List;

public interface InventoryRepository {

	Product getBy(ProductRef ref);

	void save(Product product);

    List<InventoryEntity> getAll();
}
