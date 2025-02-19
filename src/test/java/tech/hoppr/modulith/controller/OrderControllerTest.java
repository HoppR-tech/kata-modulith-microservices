package tech.hoppr.modulith.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.model.OrderId;
import tech.hoppr.modulith.model.Products;
import tech.hoppr.modulith.model.Quantity;
import tech.hoppr.modulith.model.Reservation;
import tech.hoppr.modulith.repository.inventory.InventoryRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF_2;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
class OrderControllerTest {

	@MockitoBean
	OrderId.Provider idProvider;
	@Autowired
	InventoryRepository inventories;
	@Autowired
	WebTestClient client;

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

		assertThat(actualReservation)
			.contains(Reservation.builder()
				.orderId(ORDER_ID)
				.products(Products.builder()
					.addProduct(PRODUCT_REF, Quantity.of(2))
					.addProduct(PRODUCT_REF_2, Quantity.of(3))
					.build())
				.build());
	}

}
