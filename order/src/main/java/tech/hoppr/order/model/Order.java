package tech.hoppr.order.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.shared.CustomerId;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Order {

	private static final int WITHDRAWAL_PERIOD = 14;

	private final List<OrderEvent> occurredEvents = new ArrayList<>();

	private final OrderId id;
	private final CustomerId customerId;
	@Singular
	private final List<Item> items;
	private final Instant placedAt;
	private Instant cancelledAt;

	private Order(OrderPlaced event) {
		this.occurredEvents.add(event);
		this.id = event.orderId();
		this.customerId = event.customerId();
		this.items = event.items().stream()
			.map(item -> Item.builder()
				.product(item.productRef())
				.quantity(item.quantity())
				.build())
			.toList();
		this.placedAt = event.placedAt();
	}

	public static CreateBuilder place() {
		return new CreateBuilder();
	}

	public List<OrderEvent> occurredEvents() {
		return List.copyOf(occurredEvents);
	}

	public Optional<Instant> cancelledAt() {
		return Optional.ofNullable(cancelledAt);
	}

	public void cancel(Instant cancelledAt) {
		Duration duration = Duration.between(placedAt, cancelledAt);

		if (duration.toDays() > WITHDRAWAL_PERIOD) {
			throw new WithdrawalPeriodExceeded(id);
		}

		OrderCancelled event = OrderCancelled.builder()
			.orderId(id)
			.customerId(customerId)
			.cancelledAt(cancelledAt)
			.build();

		this.occurredEvents.add(event);
		this.apply(event);
	}

	private void apply(OrderCancelled event) {
		this.cancelledAt = event.cancelledAt();
	}

	public static class CreateBuilder {

		private OrderId id;
		private CustomerId customerId;
		private List<Item> items = new ArrayList<>();
		private Instant placedAt;

		public CreateBuilder id(OrderId id) {
			this.id = id;
			return this;
		}

		public CreateBuilder customerId(CustomerId customerId) {
			this.customerId = customerId;
			return this;
		}

		public CreateBuilder items(List<Item> items) {
			this.items = items;
			return this;
		}

		public CreateBuilder placedAt(Instant placedAt) {
			this.placedAt = placedAt;
			return this;
		}

		public Order build() {
			return new Order(OrderPlaced.builder()
				.orderId(id)
				.customerId(customerId)
				.items(items.stream()
					.map(item -> OrderPlaced.Item.builder()
						.productRef(item.product())
						.quantity(item.quantity())
						.build())
					.toList())
				.placedAt(placedAt)
				.build());
		}

	}
}
