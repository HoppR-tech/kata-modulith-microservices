package tech.hoppr.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Product;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;
import tech.hoppr.modulith.repository.inventory.InventoryRepository;
import tech.hoppr.modulith.service.InventoryService;

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
