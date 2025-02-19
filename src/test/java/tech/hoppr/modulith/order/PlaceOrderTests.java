package tech.hoppr.modulith.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.order.model.OrderPlaced;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.MessageBus;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.modulith.order.assertions.ItemAssertions.assertThat;
import static tech.hoppr.modulith.order.assertions.OrderAssertions.assertThat;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class PlaceOrderTests {

	@MockitoBean
	OrderId.Provider idProvider;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orders;
	@MockitoBean
	MessageBus messageBus;

	@BeforeEach
	void setUp() {
		when(idProvider.get())
			.thenReturn(ORDER_ID);
	}

	private Order placeOrder() {
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

		orderPlacedShouldBeEmitted();
	}

	private void orderPlacedShouldBeEmitted() {
		verify(messageBus).emit(OrderPlaced.builder()
			.orderId(ORDER_ID)
			.item(Item.builder()
				.product(PRODUCT_REF)
				.quantity(Quantity.of(2))
				.build())
			.build());
	}

}
