package tech.hoppr.modulith.repository.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.modulith.entity.EntityPersister;
import tech.hoppr.modulith.entity.OrderEntity;
import tech.hoppr.modulith.entity.OrderItemEntity;
import tech.hoppr.modulith.model.Item;
import tech.hoppr.modulith.model.Order;
import tech.hoppr.modulith.model.OrderId;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;

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

	private OrderEntity toEntity(Order order) {
		String orderId = order.id().value();

		return OrderEntity.builder()
			.id(orderId)
			.items(order.items().stream()
				.map(this::toItemEntity)
				.toList())
			.build();
	}

	private OrderItemEntity toItemEntity(Item item) {
		return OrderItemEntity.builder()
			.productRef(item.product().value())
			.quantity(item.quantity().value())
			.build();
	}
}
