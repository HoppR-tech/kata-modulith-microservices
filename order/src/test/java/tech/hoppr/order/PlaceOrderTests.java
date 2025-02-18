package tech.hoppr.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.order.model.OrderPlaced;
import tech.hoppr.order.repository.OrderRepository;
import tech.hoppr.order.service.OrderService;
import tech.hoppr.shared.MessageBus;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.hoppr.order.assertions.OrderAssertions.assertThat;
import static tech.hoppr.order.assertions.ItemAssertions.assertThat;
import static tech.hoppr.order.fixtures.OrderFixtures.ORDER_ID;
import static tech.hoppr.order.fixtures.OrderFixtures.PRODUCT_REF;

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

		verify(messageBus).emit(OrderPlaced.builder()
			.orderId(ORDER_ID)
			.items(List.of(OrderPlaced.Item.builder()
				.productRef(PRODUCT_REF)
				.quantity(Quantity.of(2))
				.build()))
			.build());
	}


}
