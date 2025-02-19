package tech.hoppr.modulith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Order;
import tech.hoppr.modulith.model.OrderId;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;
import tech.hoppr.modulith.repository.order.OrderRepository;
import tech.hoppr.modulith.service.InventoryService;
import tech.hoppr.modulith.service.OrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.assertions.ItemAssertions.assertThat;
import static tech.hoppr.modulith.assertions.OrderAssertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;

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
	InventoryService inventoryService;

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

	@Test
	void call_inventory_to_reduce_the_reserve_products() {
		placeOrder();

		ArgumentCaptor<List<Item>> captor = ArgumentCaptor.forClass(List.class);
		verify(inventoryService).reserve(eq(ORDER_ID), captor.capture());

		List<Item> actualItems = captor.getValue();

		assertThat(actualItems).first()
			.satisfies(item -> assertThat(item)
				.hasProductRef(ProductRef.of("123"))
				.hasQuantity(Quantity.of(2)));
	}


}
