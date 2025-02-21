package tech.hoppr.order.repository.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
public class OrderEntity {

	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	private String id;

	@Column(name = "customer_id")
	private String customerId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private List<OrderItemEntity> items;

	@Column(name = "placed_at")
	private Timestamp placedAt;

	@Column(name = "cancelled_at")
	private Timestamp cancelledAt;

	@Builder(toBuilder = true)
	public OrderEntity(
		String id,
		String customerId,
		List<OrderItemEntity> items,
		Timestamp placedAt,
		Timestamp cancelledAt
	) {
		this.id = id;
		this.customerId = customerId;
		this.items = items == null
			? List.of()
			: items.stream()
			.map(item -> item.withOrder(this))
			.toList();
		this.placedAt = placedAt;
		this.cancelledAt = cancelledAt;
	}

}
