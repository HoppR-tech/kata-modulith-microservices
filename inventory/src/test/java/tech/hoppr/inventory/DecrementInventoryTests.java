package tech.hoppr.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.inventory.model.Product;
import tech.hoppr.inventory.model.Products;
import tech.hoppr.inventory.repository.InventoryRepository;
import tech.hoppr.inventory.service.InventoryService;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import static tech.hoppr.inventory.assertions.ProductAssertions.assertThat;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class DecrementInventoryTests {

	@Autowired
	InventoryService inventoryService;
	@Autowired
	InventoryRepository inventory;

	@Test
	void decrement_product_when_order_is_placed() {
		inventory.save(Product.builder()
			.ref(ProductRef.of("123"))
			.quantity(Quantity.of(5))
			.build());

		inventoryService.decrement(Products.builder()
			.addProduct(ProductRef.of("123"), Quantity.of(2))
			.build());

		Product actualProduct = inventory.getBy(ProductRef.of("123"));

		assertThat(actualProduct).hasQuantity(Quantity.of(3));
	}
}
