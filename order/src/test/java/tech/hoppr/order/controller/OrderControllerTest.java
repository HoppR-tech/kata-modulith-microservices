package tech.hoppr.order.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.hoppr.order.TestcontainersConfiguration;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.service.OrderService;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.List;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
class OrderControllerTest {

	@MockitoBean
	OrderService orderService;
	@Autowired
	WebTestClient client;

	@Test
	void place_an_order() {
		WebTestClient.ResponseSpec exchange = client.post()
			.uri("/orders/place")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("""
				{
					"items": [
						{ "productRef": "1234", "quantity": 2},
						{ "productRef": "4567", "quantity": 3}
					]
				}
				""")
			.exchange();

		exchange.expectBody().isEmpty();
		exchange.expectStatus().is2xxSuccessful();

		verify(orderService).placeOrder(List.of(
			Item.builder()
				.product(ProductRef.of("1234"))
				.quantity(Quantity.of(2))
				.build(),
			Item.builder()
				.product(ProductRef.of("4567"))
				.quantity(Quantity.of(3))
				.build()
		));
	}

}
