package tech.hoppr.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.shared.Item;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.model.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

import static tech.hoppr.modulith.assertions.ProductAssertions.assertThat;

@Transactional
@SpringBootTest
public class DecrementInventoryTests {

	@Autowired
	InventoryRepository inventory;
	@Autowired
	InventoryService inventoryService;

	@Test
	void decrement_product() {
		inventory.save(Product.builder()
			.ref(ProductRef.of("123"))
			.quantity(Quantity.of(5))
			.build());

		inventoryService.decrement(List.of(Item.builder()
			.product(ProductRef.of("123"))
			.quantity(Quantity.of(2))
			.build()));

		Product actualProduct = inventory.getBy(ProductRef.of("123"));

		assertThat(actualProduct).hasQuantity(Quantity.of(3));
	}
}
