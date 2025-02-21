package tech.hoppr.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.convention.TestBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.order.model.OrderPlaced;
import tech.hoppr.order.repository.OrderRepository;
import tech.hoppr.shared.CleanupDatabaseAfterEach;
import tech.hoppr.shared.MessageBus;
import tech.hoppr.shared.Quantity;
import tech.hoppr.shared.TestcontainersConfiguration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.hoppr.order.assertions.OrderAssertions.assertThat;
import static tech.hoppr.order.fixtures.OrderFixtures.CUSTOMER_ID;
import static tech.hoppr.order.fixtures.OrderFixtures.ORDER_ID;
import static tech.hoppr.order.fixtures.OrderFixtures.PRODUCT_REF;
import static tech.hoppr.order.fixtures.OrderFixtures.PRODUCT_REF_2;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
@ExtendWith(CleanupDatabaseAfterEach.class)
public class PlaceOrderTests {

	static final Instant FIXED_NOW = Instant.ofEpochMilli(1234567890L);

	@Autowired
	WebTestClient client;
	@MockitoBean
	OrderId.Provider idProvider;
	@TestBean
	Clock clock;
	@Autowired
	OrderRepository orders;
	@MockitoBean
	MessageBus messageBus;

	static Clock clock() {
		return Clock.fixed(FIXED_NOW, ZoneId.of("UTC"));
	}

	@BeforeEach
	void setUp() {
		when(idProvider.get())
			.thenReturn(ORDER_ID);
	}

	private WebTestClient.ResponseSpec callApi() {
		return client.post()
			.uri("/orders/place")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("""
				{
					"customerId": "CUSTOMER_ID",
					"items": [
						{ "productRef": "123", "quantity": 2},
						{ "productRef": "456", "quantity": 3}
					]
				}
				""")
			.exchange();
	}

	@Test
	void place_an_order() {
		WebTestClient.ResponseSpec actualResponse = callApi();

		actualResponse
			.expectStatus().isAccepted()
			.expectBody().json("""
				{
					"id": "FR001"
				}
				""");

		orderShouldBeWellSaved();
		orderPlacedShouldBeEmitted();
	}

	private void orderShouldBeWellSaved() {
		Order actualOrder = orders.getBy(ORDER_ID);

		assertThat(actualOrder)
			.hasId(ORDER_ID)
			.isPlacedSince(Instant.ofEpochMilli(1234567890L));

		assertThat(actualOrder).items()
			.containsExactlyInAnyOrder(
				Item.builder()
					.product(PRODUCT_REF)
					.quantity(Quantity.of(2))
					.build(),
				Item.builder()
					.product(PRODUCT_REF_2)
					.quantity(Quantity.of(3))
					.build()
			);

		assertThat(actualOrder)
			.isPlacedSince(FIXED_NOW);
	}

	private void orderPlacedShouldBeEmitted() {
		verify(messageBus).emit(OrderPlaced.builder()
			.orderId(ORDER_ID)
			.customerId(CUSTOMER_ID)
			.item(Item.builder()
				.product(PRODUCT_REF)
				.quantity(Quantity.of(2))
				.build())
			.item(Item.builder()
				.product(PRODUCT_REF_2)
				.quantity(Quantity.of(3))
				.build())
			.placedAt(FIXED_NOW)
			.build());
	}

}
