package tech.hoppr.modulith.inventory.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.modulith.order.model.OrderId;

import java.util.List;
import java.util.stream.Stream;

@Builder
@Accessors(fluent = true)
@ToString
public final class Reservation {

	@Getter
	private final OrderId orderId;
	private final Products products;

	public static Reservation create(OrderId orderId, List<Product> products) {
		return Reservation.builder()
			.orderId(orderId)
			.products(Products.from(products))
			.build();
	}

	public Stream<Product> stream() {
		return products.stream();
	}
}
