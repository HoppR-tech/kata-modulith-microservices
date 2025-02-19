package tech.hoppr.modulith.inventory.repository.jpa;

import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.inventory.model.Product;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.inventory.model.Products;
import tech.hoppr.modulith.shared.Quantity;
import tech.hoppr.modulith.inventory.model.Reservation;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DbInventoryRepository implements InventoryRepository {

	private final JpaInventories jpa;

	@Override
	public void save(Reservation reservation) {
		String orderId = reservation.orderId().value();

		List<ReservationEntity> entities = reservation.stream()
			.map(product -> toEntity(product, orderId))
			.toList();

		jpa.saveAll(entities);
	}

	@Override
	public Optional<Reservation> reservationOf(OrderId orderId) {
		List<ReservationEntity> entities = jpa.findAllByOrderId(orderId.value());

		if (entities.isEmpty()) {
			return Optional.empty();
		}

		Products.ProductsBuilder builder = Products.builder();
		entities.forEach(entity ->
			builder.addProduct(
				ProductRef.of(entity.getProductRef()),
				Quantity.of(entity.getQuantity())
			));

		return Optional.of(Reservation.builder()
			.orderId(orderId)
			.products(builder.build())
			.build());
	}

	private static ReservationEntity toEntity(Product product, String orderId) {
		return ReservationEntity.builder()
			.orderId(orderId)
			.productRef(product.ref().value())
			.quantity(product.quantity().value())
			.build();
	}
}
