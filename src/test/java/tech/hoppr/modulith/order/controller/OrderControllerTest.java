package tech.hoppr.modulith.order.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
class OrderControllerTest {

	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	WebTestClient client;

	@Test
	void place_an_order() {
		inventoryRepository.save(Product.builder()
			.ref(ProductRef.of("1234"))
			.quantity(Quantity.of(5))
			.build());

		inventoryRepository.save(Product.builder()
			.ref(ProductRef.of("4567"))
			.quantity(Quantity.of(10))
			.build());

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
	}

}
