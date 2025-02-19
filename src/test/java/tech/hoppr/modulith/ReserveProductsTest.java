package tech.hoppr.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Products;
import tech.hoppr.modulith.model.Reservation;
import tech.hoppr.modulith.repository.inventory.InventoryRepository;
import tech.hoppr.modulith.service.InventoryService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF_2;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.QTY_ONE;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.QTY_TEN;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class ReserveProductsTest {

	@Autowired
	InventoryRepository inventory;
	@Autowired
	InventoryService inventoryService;

	@Test
	void reserve_products() {
		inventoryService.reserve(ORDER_ID, List.of(
			Item.builder()
				.product(PRODUCT_REF)
				.quantity(QTY_ONE)
				.build(),
			Item.builder()
				.product(PRODUCT_REF_2)
				.quantity(QTY_TEN)
				.build()
		));

		Optional<Reservation> actualResult = inventory.reservationOf(ORDER_ID);

		assertThat(actualResult)
			.contains(Reservation.builder()
				.orderId(ORDER_ID)
				.products(Products.builder()
					.addProduct(PRODUCT_REF, QTY_ONE)
					.addProduct(PRODUCT_REF_2, QTY_TEN)
					.build())
				.build());
	}
}
