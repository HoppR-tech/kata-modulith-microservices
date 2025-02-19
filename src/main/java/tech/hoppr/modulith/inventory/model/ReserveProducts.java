package tech.hoppr.modulith.inventory.model;

import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.ArrayList;
import java.util.List;

public record ReserveProducts(OrderId orderId, List<Product> products) {

	public ReserveProducts(OrderId orderId, List<Product> products) {
		this.orderId = orderId;
		this.products = List.copyOf(products);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private OrderId orderId;
		private List<Product> products = new ArrayList<>();

		public Builder orderId(OrderId orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder product(ProductRef productRef, Quantity quantity) {
			this.products.add(new Product(productRef, quantity));
			return this;
		}

		public ReserveProducts build() {
			return new ReserveProducts(orderId, products);
		}

	}

}
