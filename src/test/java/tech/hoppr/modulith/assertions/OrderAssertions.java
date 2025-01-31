package tech.hoppr.modulith.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Order;

@RequiredArgsConstructor
public class OrderAssertions {

	private final Order order;

	public OrderAssertions hasAnId() {
		Assertions.assertThat(order).isNotNull();
		return this;
	}

	public ListAssert<Item> items() {
		return Assertions.assertThat(order.items());
	}

	public static OrderAssertions assertThat(Order order) {
		return new OrderAssertions(order);
	}

}
