package tech.hoppr.inventory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.RabbitMQContainer;
import tech.hoppr.inventory.model.Products;
import tech.hoppr.inventory.model.Reservation;
import tech.hoppr.inventory.ports.in.OrderCancelled;
import tech.hoppr.inventory.repository.InventoryRepository;
import tech.hoppr.shared.CleanupDatabaseAfterEach;
import tech.hoppr.shared.PostgreSQLConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.ORDER_ID;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.PRODUCT_REF;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.PRODUCT_REF_2;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.QTY_ONE;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.QTY_TEN;

@Transactional
@SpringBootTest
@Import({
	PostgreSQLConfiguration.class,
	RabbitMQContainer.class,
})
@ExtendWith(CleanupDatabaseAfterEach.class)
public class CancelReservationTest {

	@Autowired
	InventoryRepository inventory;
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	void cancel_products() {
		inventory.save(Reservation.builder()
			.orderId(ORDER_ID)
			.products(Products.builder()
				.product(PRODUCT_REF, QTY_ONE)
				.product(PRODUCT_REF_2, QTY_TEN)
				.build())
			.build());

		rabbitTemplate.convertSendAndReceive("order.cancelled", OrderCancelled.builder()
			.orderId(ORDER_ID.value())
			.build());

		Optional<Reservation> actualResult = inventory.reservationOf(ORDER_ID);

		assertThat(actualResult).isEmpty();
	}
}
