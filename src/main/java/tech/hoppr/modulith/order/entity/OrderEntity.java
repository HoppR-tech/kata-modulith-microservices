package tech.hoppr.modulith.order.entity;

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

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
public class OrderEntity {

	@Builder(toBuilder = true)
	public OrderEntity(String id, List<OrderItemEntity> items) {
		this.id = id;
		this.items = items == null
			? List.of()
			: items.stream()
			.map(item -> item.withOrder(this))
			.toList();
	}

	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	private String id;

    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
    private List<OrderItemEntity> items;

}
