package tech.hoppr.microservice.order;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.wiremock.spring.EnableWireMock;
import tech.hoppr.microservice.order.model.Quantity;
import tech.hoppr.microservice.order.published.OrderPlaced;
import tech.hoppr.microservice.order.shared.MessageEmitter;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static tech.hoppr.microservice.order.fixtures.OrderFixtures.ORDER_ID;
import static tech.hoppr.microservice.order.fixtures.OrderFixtures.PRODUCT_REF;

@Transactional
@SpringBootTest(properties = "partner.inventory.base-url=${wiremock.server.baseUrl}")
@EnableWireMock
public class DecrementInventoryOnOrderPlacedTest {

	@Autowired
	MessageEmitter emitter;

	@Test
	void notify_inventory_service() {
		stubFor(post("/inventories/on-order-placed")
			.willReturn(WireMock.aResponse().withStatus(202)));

		emitter.emit(OrderPlaced.builder()
			.orderId(ORDER_ID)
			.items(List.of(OrderPlaced.Item.builder()
				.productRef(PRODUCT_REF)
				.quantity(Quantity.of(10))
				.build()))
			.build());

		verify(postRequestedFor(urlPathEqualTo("/inventories/on-order-placed"))
			.withRequestBody(equalToJson("""
				{
					"orderId": "FR001",
					"items": [{ "productRef": "123", "quantity": 10 }]
				}
				""")));

	}
}
