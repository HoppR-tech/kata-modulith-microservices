package tech.hoppr.modulith.repository.inventory;

import tech.hoppr.modulith.entity.InventoryEntity;
import tech.hoppr.modulith.model.Product;
import tech.hoppr.modulith.model.ProductRef;

import java.util.List;

public interface InventoryRepository {

	Product getBy(ProductRef ref);

	void save(Product product);

    List<InventoryEntity> getAll();
}
