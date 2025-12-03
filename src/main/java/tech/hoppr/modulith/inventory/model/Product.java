package tech.hoppr.modulith.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

@Builder
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Product {

	private final ProductRef ref;
	private Quantity quantity;

	public void decrement(Quantity quantity) {
		this.quantity = this.quantity.decrement(quantity.value());
	}

}
