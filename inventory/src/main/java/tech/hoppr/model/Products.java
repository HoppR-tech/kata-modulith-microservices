package tech.hoppr.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public final class Products {

	private final List<Product> products;

	public Products(List<Product> products) {
		this.products = List.copyOf(products);
	}

	public static Products from(List<Product> products) {
		return new Products(products);
	}

	public Stream<Product> stream() {
		return products.stream();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private final List<Product> products = new ArrayList<>();

		public Builder product(ProductRef productRef, Quantity quantity) {
			this.products.add(new Product(productRef, quantity));
			return this;
		}

		public Products build() {
			return new Products(products);
		}

	}
}
