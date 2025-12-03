package tech.hoppr.modulith.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.modulith.order.published.OrderCanceled;
import tech.hoppr.modulith.order.published.OrderEvent;
import tech.hoppr.modulith.order.published.OrderPlaced;
import tech.hoppr.modulith.shared.OrderId;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Order {

	private final List<OrderEvent> occurredEvents = new ArrayList<>();

	private final OrderId id;
	private final List<Item> items;
	private final Instant placedAt;
	private Instant canceledAt;

	private Order(OrderPlaced event) {
		this.occurredEvents.add(event);
		this.id = event.orderId();
		this.placedAt = event.placedAt();
		this.items = event.items().stream()
			.map(item -> Item.builder()
				.product(item.productRef())
				.quantity(item.quantity())
				.build())
			.toList();
	}

	public void cancel(Instant requestedAt) {
		if (requestedAt.isAfter(placedAt.plus(14, ChronoUnit.DAYS))) {
			throw new WithdrawalPeriodHasExpired(id);
		}

		OrderCanceled event = OrderCanceled.builder()
			.orderId(id)
			.canceledAt(requestedAt)
			.build();

		this.occurredEvents.add(event);
		this.apply(event);
	}

	private void apply(OrderCanceled event) {
		this.canceledAt = event.canceledAt();
	}

	public Optional<Instant> canceledAt() {
		return Optional.ofNullable(canceledAt);
	}

	public boolean isCanceled() {
		return canceledAt != null;
	}

	public List<OrderEvent> occurredEvents() {
		return List.copyOf(occurredEvents);
	}

	public void commit() {
		this.occurredEvents.clear();
	}

	public static PlaceBuilder place() {
		return new PlaceBuilder();
	}

	public static final class PlaceBuilder {

		private OrderId id;
		private List<Item> items = new ArrayList<>();
		private Instant requestedAt = Instant.now(Clock.systemUTC());

		public PlaceBuilder id(OrderId id) {
			this.id = id;
			return this;
		}

		public PlaceBuilder items(List<Item> items) {
			this.items.addAll(items);
			return this;
		}

		public PlaceBuilder requestedAt(Instant requestedAt) {
			this.requestedAt = requestedAt;
			return this;
		}

		public Order build() {
			return new Order(OrderPlaced.builder()
				.orderId(id)
				.items(items.stream()
					.map(item -> OrderPlaced.Item.builder()
						.productRef(item.product())
						.quantity(item.quantity())
						.build())
					.toList())
				.placedAt(requestedAt)
				.build());
		}

	}

}
