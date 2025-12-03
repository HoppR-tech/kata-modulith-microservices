package tech.hoppr.modulith.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;

import java.time.Instant;

@RequiredArgsConstructor
public class OrderAssertions {

	private final Order order;

	public OrderAssertions hasAnId() {
		Assertions.assertThat(order).isNotNull();
		return this;
	}

	public OrderAssertions isCanceled() {
		Assertions.assertThat(order.isCanceled()).isTrue();
		return this;
	}

	public OrderAssertions isCanceledAt(Instant expected) {
		Assertions.assertThat(order.canceledAt()).contains(expected);
		return this;
	}

	public OrderAssertions isPlacedAt(Instant expected) {
		Assertions.assertThat(order.placedAt()).isEqualTo(expected);
		return this;
	}

	public ListAssert<Item> items() {
		return Assertions.assertThat(order.items());
	}

	public static OrderAssertions assertThat(Order order) {
		return new OrderAssertions(order);
	}
}
