package tech.hoppr.modulith.model;

import lombok.Builder;

import java.util.stream.Stream;

@Builder
public record Reservation(OrderId orderId, Products products) {

	public static Reservation create(OrderId orderId, Products products) {
		return Reservation.builder()
			.orderId(orderId)
			.products(products)
			.build();
	}

	public Stream<Product> stream() {
		return products.stream();
	}
}
