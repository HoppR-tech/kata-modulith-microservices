package tech.hoppr.modulith;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Order;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;
import tech.hoppr.modulith.repository.OrderRepository;
import tech.hoppr.modulith.service.InventoryService;
import tech.hoppr.modulith.service.OrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static tech.hoppr.modulith.ItemAssertions.assertThat;
import static tech.hoppr.modulith.OrderAssertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class PlaceOrderTests {

	@Autowired
	OrderService orderService;
	@MockitoBean
	OrderRepository orders;
	@MockitoBean
	InventoryService inventoryService;

	private void placeOrder() {
		orderService.order(List.of(
			Item.builder()
				.product(ProductRef.of("123"))
				.quantity(Quantity.of(2))
				.build()
		));
	}

	@Test
	void place_an_order() {
		placeOrder();

		ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
		verify(orders).save(captor.capture());

		Order actualOrder = captor.getValue();

		assertThat(actualOrder).hasAnId();

		assertThat(actualOrder).items().first()
			.satisfies(item -> assertThat(item)
				.hasProductRef(ProductRef.of("123"))
				.hasQuantity(Quantity.of(2)));
	}

	@Test
	void call_inventory_to_reduce_the_amount_of_products() {
		placeOrder();

		ArgumentCaptor<List<Item>> captor = ArgumentCaptor.forClass(List.class);
		verify(inventoryService).decrement(captor.capture());

		List<Item> actualItems = captor.getValue();

		assertThat(actualItems).first()
			.satisfies(item -> assertThat(item)
				.hasProductRef(ProductRef.of("123"))
				.hasQuantity(Quantity.of(2)));
	}


}
