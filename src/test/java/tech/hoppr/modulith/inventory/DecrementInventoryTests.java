package tech.hoppr.modulith.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.order.model.OrderPlaced;
import tech.hoppr.modulith.shared.MessageBus;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

import static tech.hoppr.modulith.assertions.ProductAssertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class DecrementInventoryTests {

	@Autowired
	InventoryRepository inventory;
	@Autowired
	MessageBus messageBus;

	@Test
	void decrement_product_when_order_is_placed() {
		inventory.save(Product.builder()
			.ref(ProductRef.of("123"))
			.quantity(Quantity.of(5))
			.build());

		messageBus.emit(OrderPlaced.builder()
			.orderId(ORDER_ID)
			.items(List.of(OrderPlaced.Item.builder()
				.productRef(ProductRef.of("123"))
				.quantity(Quantity.of(2))
				.build()))
			.build());

		Product actualProduct = inventory.getBy(ProductRef.of("123"));

		assertThat(actualProduct).hasQuantity(Quantity.of(3));
	}
}
