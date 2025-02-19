package tech.hoppr.modulith.inventory.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class Products {

	private final List<Product> products;

	@Builder
	private Products(@Singular("addProduct") Map<ProductRef, Quantity> quantityPerProducts) {
		this.products = quantityPerProducts.entrySet().stream()
			.map(entry -> Product.builder()
				.ref(entry.getKey())
				.quantity(entry.getValue())
				.build())
			.toList();
	}

	public Stream<Product> stream() {
		return products.stream();
	}
}
