package tech.hoppr.order.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderId;

import java.time.Instant;

@RequiredArgsConstructor
public class OrderAssertions {

	private final Order order;

	public OrderAssertions hasId(OrderId expectedId) {
		Assertions.assertThat(order.id()).isEqualTo(expectedId);
		return this;
	}

	public ListAssert<Item> items() {
		return Assertions.assertThat(order.items());
	}

	public void isPlacedSince(Instant expected) {
		Assertions.assertThat(order.placedAt())
			.isEqualTo(expected);
	}

	public void isCancelledSince(Instant expected) {
		Assertions.assertThat(order.cancelledAt())
			.contains(expected);
	}

	public static OrderAssertions assertThat(Order order) {
		return new OrderAssertions(order);
	}
}
