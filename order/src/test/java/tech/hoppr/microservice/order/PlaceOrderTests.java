package tech.hoppr.microservice.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.Order;
import tech.hoppr.microservice.order.published.OrderPlaced;
import tech.hoppr.microservice.order.repository.OrderRepository;
import tech.hoppr.microservice.order.service.OrderService;
import tech.hoppr.microservice.order.shared.OrderId;
import tech.hoppr.microservice.order.shared.ProductRef;
import tech.hoppr.microservice.order.shared.Quantity;
import tech.hoppr.microservice.order.testing.time.AutoConfigureTimeControl;
import tech.hoppr.microservice.order.testing.time.TestableClock;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static tech.hoppr.microservice.order.assertions.OrderAssertions.assertThat;
import static tech.hoppr.microservice.order.assertions.ItemAssertions.assertThat;
import static tech.hoppr.microservice.order.assertions.EventCaptorAssertions.assertThat;
import static tech.hoppr.microservice.order.fixtures.OrderFixtures.ORDER_ID;
import static tech.hoppr.microservice.order.fixtures.OrderFixtures.PRODUCT_REF;

@Transactional
@SpringBootTest
@AutoConfigureTimeControl
public class PlaceOrderTests {

	@MockitoBean
	OrderId.Provider idProvider;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orders;
	@Autowired
	EventCaptor eventCaptor;
	@Autowired
	TestableClock clock;

	@BeforeEach
	void setUp() {
		when(idProvider.get())
			.thenReturn(ORDER_ID);
	}

	private Order placeOrder() {
		clock.setTo(Instant.ofEpochMilli(1000));

		orderService.placeOrder(List.of(
			Item.builder()
				.product(PRODUCT_REF)
				.quantity(Quantity.of(2))
				.build()
		));

		return orders.getBy(ORDER_ID);
	}

	@Test
	void place_an_order() {
		Order actualOrder = placeOrder();

		assertThat(actualOrder).hasAnId();

		assertThat(actualOrder).items().first()
			.satisfies(item -> assertThat(item)
				.hasProductRef(ProductRef.of("123"))
				.hasQuantity(Quantity.of(2)));

		assertThat(actualOrder).isPlacedAt(Instant.ofEpochMilli(1000));
	}

	@Test
	void notify_that_order_is_placed() {
		placeOrder();

		assertThat(eventCaptor).hasCaptured(OrderPlaced.builder()
			.orderId(ORDER_ID)
			.items(List.of(
				OrderPlaced.Item.builder()
					.productRef(PRODUCT_REF)
					.quantity(Quantity.of(2))
					.build()
			))
			.placedAt(Instant.ofEpochMilli(1000))
			.build());
	}

}
