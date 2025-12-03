package tech.hoppr.modulith.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

@RequiredArgsConstructor
public class ItemAssertions {

	private final Item item;

	public ItemAssertions hasProductRef(ProductRef expected) {
		Assertions.assertThat(item.product()).isEqualTo(expected);
		return this;
	}

	public ItemAssertions hasQuantity(Quantity expected) {
		Assertions.assertThat(item.quantity()).isEqualTo(expected);
		return this;
	}

	public static ItemAssertions assertThat(Item item) {
		return new ItemAssertions(item);
	}

}
