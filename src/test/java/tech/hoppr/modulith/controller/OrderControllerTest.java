package tech.hoppr.modulith.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.hoppr.modulith.model.Product;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;
import tech.hoppr.modulith.repository.inventory.InventoryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
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
