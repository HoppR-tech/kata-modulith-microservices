package tech.hoppr.modulith.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.fixtures.CleanupDatabaseAfterEach;
import tech.hoppr.modulith.inventory.model.Reservation;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF_2;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.QTY_TEN;
import static tech.hoppr.modulith.inventory.assertions.ReservationAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
@ExtendWith(CleanupDatabaseAfterEach.class)
class OrderIntegrationTest {

	@MockitoBean
	OrderId.Provider idProvider;
	@Autowired
	InventoryRepository inventories;
	@Autowired
	WebTestClient client;
	@Autowired
	OrderService orderService;

	@BeforeEach
	void setUp() {
		when(idProvider.get())
			.thenReturn(ORDER_ID);
	}

	@Test
	void place_an_order() {
		WebTestClient.ResponseSpec exchange = client.post()
			.uri("/orders/place")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("""
				{
					"items": [
						{ "productRef": "123", "quantity": 2},
						{ "productRef": "456", "quantity": 3}
					]
				}
				""")
			.exchange();

		exchange.expectBody().json("""
			{
				"id": "FR001"
			}
			""");
		exchange.expectStatus().is2xxSuccessful();

		Optional<Reservation> actualReservation = inventories.reservationOf(ORDER_ID);

		assertThat(actualReservation).isPresent().get()
			.satisfies(reservation -> assertThat(reservation)
				.isForOrder(ORDER_ID)
				.products()
				.contains(PRODUCT_REF, Quantity.of(2))
				.contains(PRODUCT_REF_2, Quantity.of(3)));
	}

	@Test
	void cancel_an_order() {
		orderService.placeOrder(List.of(Item.builder()
			.product(PRODUCT_REF)
			.quantity(QTY_TEN)
			.build()));

		WebTestClient.ResponseSpec exchange = client.post()
			.uri("/orders/cancel/FR001")
			.contentType(MediaType.APPLICATION_JSON)
			.exchange();

		exchange.expectStatus().isNoContent();

		Optional<Reservation> actualReservation = inventories.reservationOf(ORDER_ID);
		assertThat(actualReservation).isEmpty();
	}

}
