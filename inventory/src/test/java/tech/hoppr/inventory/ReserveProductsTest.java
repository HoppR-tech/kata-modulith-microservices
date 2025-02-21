package tech.hoppr.inventory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.inventory.model.Reservation;
import tech.hoppr.inventory.ports.in.OrderPlaced;
import tech.hoppr.inventory.repository.InventoryRepository;
import tech.hoppr.shared.CleanupDatabaseAfterEach;
import tech.hoppr.shared.PostgreSQLConfiguration;
import tech.hoppr.shared.RabbitConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.inventory.assertions.ReservationAssertions.assertThat;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.ORDER_ID;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.PRODUCT_REF;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.PRODUCT_REF_2;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.QTY_ONE;
import static tech.hoppr.inventory.fixtures.InventoryFixtures.QTY_TEN;

@Transactional
@SpringBootTest
@Import({
	PostgreSQLConfiguration.class,
	RabbitConfiguration.class
})
@ExtendWith(CleanupDatabaseAfterEach.class)
public class ReserveProductsTest {

	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	InventoryRepository inventory;

	@Test
	void reserve_products() {
		rabbitTemplate.convertSendAndReceive("order.placed", OrderPlaced.builder()
			.orderId(ORDER_ID.value())
			.items(List.of(OrderPlaced.Item.builder()
					.productRef(PRODUCT_REF.value())
					.quantity(QTY_ONE.value())
					.build(),
				OrderPlaced.Item.builder()
					.productRef(PRODUCT_REF_2.value())
					.quantity(QTY_TEN.value())
					.build()))
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
