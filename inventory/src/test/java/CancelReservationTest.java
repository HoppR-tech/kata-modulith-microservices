package tech.hoppr.inventory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.TestcontainersConfiguration;
import tech.hoppr.fixtures.CleanupDatabaseAfterEach;
import tech.hoppr.model.CancelReservation;
import tech.hoppr.model.Products;
import tech.hoppr.model.Reservation;
import tech.hoppr.repository.InventoryRepository;
import tech.hoppr.service.InventoryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.fixtures.ApplicationFixtures.PRODUCT_REF_2;
import static tech.hoppr.fixtures.ApplicationFixtures.QTY_ONE;
import static tech.hoppr.fixtures.ApplicationFixtures.QTY_TEN;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ExtendWith(CleanupDatabaseAfterEach.class)
public class CancelReservationTest {

	@Autowired
	InventoryRepository inventory;
	@Autowired
	InventoryService inventoryService;

	@Test
	void reserve_products() {
		inventory.save(Reservation.builder()
			.orderId(ORDER_ID)
			.products(Products.builder()
				.product(PRODUCT_REF, QTY_ONE)
				.product(PRODUCT_REF_2, QTY_TEN)
				.build())
			.build());

		inventoryService.handle(CancelReservation.builder()
			.orderId(ORDER_ID)
			.build());

		Optional<Reservation> actualResult = inventory.reservationOf(ORDER_ID);

		assertThat(actualResult).isEmpty();
	}
}
