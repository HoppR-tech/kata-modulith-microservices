package tech.hoppr.modulith.loyalty.model;

import tech.hoppr.modulith.order.model.OrderId;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class History {

	private final Map<OrderId, Integer> numberOfPointsPerOrder = new HashMap<>();

	public void add(OrderId orderId, int points) {
		numberOfPointsPerOrder.put(orderId, points);
	}

	public void cancel(OrderId orderId) {
		numberOfPointsPerOrder.remove(orderId);
	}

	public int numberOfPointsFor(OrderId orderId) {
		return numberOfPointsPerOrder.getOrDefault(orderId, 0);
	}

	public int currentNumberOfPoints() {
		return numberOfPointsPerOrder.values().stream()
			.reduce(0, Integer::sum);
	}

	public void forEach(BiConsumer<OrderId, Integer> consumer) {
		numberOfPointsPerOrder.forEach(consumer);
	}
}
