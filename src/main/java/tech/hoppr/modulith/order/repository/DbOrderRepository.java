package tech.hoppr.modulith.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.published.OrderCanceled;
import tech.hoppr.modulith.order.published.OrderPlaced;
import tech.hoppr.modulith.shared.OrderId;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class DbOrderRepository implements OrderRepository {

	private final JpaRepository<OrderEntity, String> jpa;

	@Override
	public Order getBy(OrderId orderId) {
		return jpa.findById(orderId.value())
			.map(this::toOrder)
			.orElseThrow(() -> new RuntimeException("Order not found"));
	}

	private Order toOrder(OrderEntity entity) {
		return Order.builder()
			.id(OrderId.of(entity.getId()))
			.items(entity.getItems().stream()
				.map(this::toItem)
				.toList())
			.placedAt(Instant.ofEpochMilli(entity.getPlacedAt()))
			.canceledAt(Optional.ofNullable(entity.getCanceledAt())
				.map(Instant::ofEpochMilli)
				.orElse(null))
			.build();
	}

	private Item toItem(OrderItemEntity entity) {
		return Item.builder()
			.product(ProductRef.of(entity.getProductRef()))
			.quantity(Quantity.of(entity.getQuantity()))
			.build();
	}

	@Override
    public void save(Order order) {
		OrderEntity entity = toEntity(order);
		jpa.save(entity);
	}

	@Override
	public void remove(OrderId orderId) {
		jpa.deleteById(orderId.value());
	}

	private OrderEntity toEntity(Order order) {
		String orderId = order.id().value();

		return OrderEntity.builder()
			.id(orderId)
			.items(order.items().stream()
				.map(this::toItemEntity)
				.toList())
			.placedAt(order.placedAt()
				.toEpochMilli())
			.canceledAt(order.canceledAt()
				.map(Instant::toEpochMilli)
				.orElse(null))
			.build();
	}

	private OrderItemEntity toItemEntity(Item item) {
		return OrderItemEntity.builder()
			.productRef(item.product().value())
			.quantity(item.quantity().value())
			.build();
	}
}
