package tech.hoppr.modulith.repository.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.modulith.entity.InventoryEntity;
import tech.hoppr.modulith.model.Product;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;

@RequiredArgsConstructor
public class DbInventoryRepository implements InventoryRepository {

	private final JpaRepository<InventoryEntity, String> jpa;

	@Override
	public Product getBy(ProductRef ref) {
		return jpa.findById(ref.value())
			.map(this::toProduct)
			.orElseThrow(() -> new RuntimeException("Product not found"));
	}

	private Product toProduct(InventoryEntity entity) {
		return Product.builder()
			.ref(ProductRef.of(entity.getProductRef()))
			.quantity(Quantity.of(entity.getQuantity()))
			.build();
	}

	@Override
	public void save(Product product) {
		InventoryEntity entity = InventoryEntity.builder()
			.productRef(product.ref().value())
			.quantity(product.quantity().value())
			.build();

		jpa.save(entity);
	}

}
