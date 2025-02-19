package tech.hoppr.modulith.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.inventory.model.Reservation;
import tech.hoppr.modulith.inventory.model.ReserveProducts;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.inventory.service.InventoryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF_2;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.QTY_ONE;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.QTY_TEN;
import static tech.hoppr.modulith.inventory.assertions.ReservationAssertions.assertThat;

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
		inventoryService.handle(ReserveProducts.builder()
			.orderId(ORDER_ID)
			.product(PRODUCT_REF, QTY_ONE)
			.product(PRODUCT_REF_2, QTY_TEN)
			.build());

		Optional<Reservation> actualResult = inventory.reservationOf(ORDER_ID);

		assertThat(actualResult).isPresent().get()
			.satisfies(reservation -> assertThat(reservation)
				.isForOrder(ORDER_ID)
				.products()
				.contains(PRODUCT_REF, QTY_ONE)
				.contains(PRODUCT_REF_2, QTY_TEN));
	}
}
