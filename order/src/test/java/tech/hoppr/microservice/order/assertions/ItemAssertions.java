package tech.hoppr.microservice.order.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.ProductRef;
import tech.hoppr.microservice.order.model.Quantity;

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
