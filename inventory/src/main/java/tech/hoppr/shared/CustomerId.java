package tech.hoppr.shared;

public record CustomerId(String value) {

	public static CustomerId of(String value) {
		return new CustomerId(value);
	}

}
