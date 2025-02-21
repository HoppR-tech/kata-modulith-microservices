package tech.hoppr.inventory.ports.in;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record OrderPlaced(String orderId, @Singular List<Item> items) {

	@Builder
	public record Item(String productRef, int quantity) {}

}
