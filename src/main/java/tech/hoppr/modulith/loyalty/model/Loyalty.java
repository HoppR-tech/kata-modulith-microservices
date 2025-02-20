package tech.hoppr.modulith.loyalty.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.shared.CustomerId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class Loyalty {

	private final List<LoyaltyEvent> occurredEvents = new ArrayList<>();

	@Getter
	private final CustomerId customerId;
	private final History history;

	public List<LoyaltyEvent> occurredEvents() {
		return List.copyOf(occurredEvents);
	}

	public static Loyalty empty(CustomerId customerId) {
		return new Loyalty(customerId, new History());
	}

	public void computePoints(OrderId orderId, int numberOfProducts) {
		int numberOfPoints = numberOfProducts * 10;

		history.add(orderId, numberOfPoints);

		occurredEvents.add(LoyaltyPointsAdded.builder()
			.customerId(customerId)
			.orderId(orderId)
			.addedPoints(numberOfPoints)
			.build());
	}

	public void cancel(OrderId orderId) {
		history.cancel(orderId);

		occurredEvents.add(LoyaltyPointsCancelled.builder()
			.customerId(customerId)
			.orderId(orderId)
			.cancelledPoints(history.numberOfPointsFor(orderId))
			.build());
	}

	public int total() {
		return history.currentNumberOfPoints();
	}

	public static Builder builder() {
		return new Builder();
	}

	public void forEach(BiConsumer<OrderId, Integer> consumer) {
		history.forEach(consumer);
	}

	public static final class Builder {
		private final History history = new History();
		private CustomerId customerId;

		public Builder customerId(CustomerId customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder history(OrderId orderId, int numberOfPoints) {
			this.history.add(orderId, numberOfPoints);
			return this;
		}

		public Loyalty build() {
			return new Loyalty(customerId, history);
		}
	}
}
