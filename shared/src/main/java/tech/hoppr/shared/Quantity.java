package tech.hoppr.shared;

public record Quantity(int value) {

	public static final Quantity ZERO = Quantity.of(0);

	public Quantity sum(Quantity other) {
		return Quantity.of(this.value + other.value);
	}

	public static Quantity of(int value) {
		return new Quantity(value);
	}
}
