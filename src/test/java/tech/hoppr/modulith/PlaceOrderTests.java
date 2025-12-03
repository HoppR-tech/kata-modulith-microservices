package tech.hoppr.modulith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.shared.OrderId;
import tech.hoppr.modulith.order.published.OrderPlaced;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;

import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.assertions.EventCaptorAssertions.assertThat;
import static tech.hoppr.modulith.assertions.ItemAssertions.assertThat;
import static tech.hoppr.modulith.assertions.OrderAssertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;

@Transactional
@SpringBootTest
public class PlaceOrderTests {

	@MockitoBean
	OrderId.Provider idProvider;
	@MockitoBean
	InventoryService inventoryService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orders;
	@Autowired
	EventCaptor eventCaptor;

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
	}

//	@Test
//	void call_inventory_to_reduce_the_amount_of_products() {
//		placeOrder();
//
//		ArgumentCaptor<List<Item>> captor = ArgumentCaptor.forClass(List.class);
//		verify(inventoryService).decrement(captor.capture());
//
//		List<Item> actualItems = captor.getValue();
//
//		assertThat(actualItems).first()
//			.satisfies(item -> assertThat(item)
//				.hasProductRef(ProductRef.of("123"))
//				.hasQuantity(Quantity.of(2)));
//	}


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
			.build());
	}

}
