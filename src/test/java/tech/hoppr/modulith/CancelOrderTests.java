package tech.hoppr.modulith;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.published.OrderCanceled;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.OrderId;
import tech.hoppr.modulith.shared.Quantity;
import tech.hoppr.modulith.testing.time.AutoConfigureTimeControl;
import tech.hoppr.modulith.testing.time.TestableClock;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.assertions.EventCaptorAssertions.assertThat;
import static tech.hoppr.modulith.assertions.OrderAssertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;

@Transactional
@SpringBootTest
@AutoConfigureTimeControl
public class CancelOrderTests {

	@MockitoBean
	OrderId.Provider idProvider;
	@MockitoBean
	InventoryService inventoryService;
	@Autowired
	OrderRepository orders;
	@Autowired
	EventCaptor eventCaptor;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderService orderService;
	@Autowired
	TestableClock clock;

	@BeforeEach
	void setUp() {
		when(idProvider.get())
			.thenReturn(ORDER_ID);

		orderRepository.save(Order.place()
			.id(ORDER_ID)
			.items(List.of(Item.builder()
				.product(PRODUCT_REF)
				.quantity(Quantity.of(2))
				.build()))
			.build());
	}

	@AfterEach
	void tearDown() {
		orderRepository.remove(ORDER_ID);
	}

	private Order cancelOrder() {
		orderService.cancelOrder(ORDER_ID);

		return orders.getBy(ORDER_ID);
	}

	@Test
	void cancel_an_order() {
		clock.setTo(Instant.ofEpochMilli(1000));

		Order actualOrder = cancelOrder();

		assertThat(actualOrder)
			.hasAnId()
			.isCanceled()
			.isCanceledAt(Instant.ofEpochMilli(1000));
	}

	@Test
	void notify_that_order_is_canceled() {
		cancelOrder();

		assertThat(eventCaptor).hasCaptured(OrderCanceled.builder()
			.orderId(ORDER_ID)
			.build());
	}

}
