package tech.hoppr.inventory.model;

public record OrderId(String value) {

	public static OrderId of(String value) {
		return new OrderId(value);
	}

}
