package tech.hoppr.microservice.order.model;

public record Quantity(int value) {

	public static Quantity of(int value) {
		return new Quantity(value);
	}
}
