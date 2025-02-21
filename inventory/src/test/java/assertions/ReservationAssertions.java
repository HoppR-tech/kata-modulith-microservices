package tech.hoppr.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import tech.hoppr.model.Product;
import tech.hoppr.model.Reservation;
import tech.hoppr.shared.OrderId;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.List;

@RequiredArgsConstructor
public class ReservationAssertions {

	private final Reservation reservation;

	public ReservationAssertions isForOrder(OrderId orderId) {
		Assertions.assertThat(reservation.orderId()).isEqualTo(orderId);
		return this;
	}

	public ProductAssertions products() {
		List<Product> products = reservation.stream().toList();
		return new ProductAssertions(products);
	}

	public static ReservationAssertions assertThat(Reservation reservation) {
		return new ReservationAssertions(reservation);
	}

	@RequiredArgsConstructor
	public static class ProductAssertions {

		private final List<Product> products;

		public ProductAssertions contains(ProductRef productRef, Quantity quantity) {
			Assertions.assertThat(products)
				.contains(Product.builder()
					.ref(productRef)
					.quantity(quantity)
					.build());

			return this;
		}
	}
}
