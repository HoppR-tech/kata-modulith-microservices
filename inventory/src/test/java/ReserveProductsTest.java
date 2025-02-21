package tech.hoppr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.TestcontainersConfiguration;
import tech.hoppr.fixtures.CleanupDatabaseAfterEach;
import tech.hoppr.model.Reservation;
import tech.hoppr.model.ReserveProducts;
import tech.hoppr.repository.InventoryRepository;
import tech.hoppr.service.InventoryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.fixtures.ApplicationFixtures.PRODUCT_REF_2;
import static tech.hoppr.fixtures.ApplicationFixtures.QTY_ONE;
import static tech.hoppr.fixtures.ApplicationFixtures.QTY_TEN;
import static tech.hoppr.assertions.ReservationAssertions.assertThat;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ExtendWith(CleanupDatabaseAfterEach.class)
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
