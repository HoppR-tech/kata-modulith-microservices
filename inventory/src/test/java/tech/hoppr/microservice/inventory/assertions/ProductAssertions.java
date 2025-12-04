package tech.hoppr.microservice.inventory.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import tech.hoppr.microservice.inventory.model.Product;
import tech.hoppr.microservice.inventory.model.Quantity;

@RequiredArgsConstructor
public class ProductAssertions {

	private final Product product;

	public ProductAssertions hasQuantity(Quantity expected) {
		Assertions.assertThat(product.quantity()).isEqualTo(expected);
		return this;
	}

	public static ProductAssertions assertThat(Product product) {
		return new ProductAssertions(product);
	}

}
