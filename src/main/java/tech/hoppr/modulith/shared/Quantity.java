package tech.hoppr.modulith.shared;

public record Quantity(int value) {

	public Quantity decrement(int value) {
		return Quantity.of(this.value - value);
	}

	public static Quantity of(int value) {
		return new Quantity(value);
	}
}
