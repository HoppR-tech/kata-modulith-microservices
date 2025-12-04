package tech.hoppr.microservice.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.Order;
import tech.hoppr.microservice.order.published.OrderCanceled;
import tech.hoppr.microservice.order.published.OrderPlaced;
import tech.hoppr.microservice.order.shared.OrderId;
import tech.hoppr.microservice.order.shared.ProductRef;
import tech.hoppr.microservice.order.shared.Quantity;

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
		order.occurredEvents().forEach(event -> {
			switch (event) {
				case OrderPlaced e -> apply(e);
				case OrderCanceled e -> apply(e);
				default -> throw new IllegalArgumentException("Unexpected event type: " + event.getClass().getName());
			}
		});

		order.commit();
	}

	@Override
	public void remove(OrderId orderId) {
		jpa.deleteById(orderId.value());
	}

	private void apply(OrderPlaced event) {
		OrderEntity entity = OrderEntity.builder()
			.id(event.orderId().value())
			.items(event.items().stream()
				.map(this::toItemEntity)
				.toList())
			.placedAt(event.placedAt()
				.toEpochMilli())
			.build();

		jpa.save(entity);
	}

	private OrderItemEntity toItemEntity(OrderPlaced.Item item) {
		return OrderItemEntity.builder()
			.productRef(item.productRef().value())
			.quantity(item.quantity().value())
			.build();
	}

	private void apply(OrderCanceled event) {
		OrderEntity entity = jpa.getReferenceById(event.orderId().value());
		entity.setCanceledAt(event.canceledAt().toEpochMilli());
		jpa.save(entity);
	}
}
