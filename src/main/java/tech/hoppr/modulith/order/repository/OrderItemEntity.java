package tech.hoppr.modulith.order.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Table(name = "order_items")
@Entity
@Builder
@NoArgsConstructor
@With
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class OrderItemEntity {

	@Id
	@UuidGenerator
	@EqualsAndHashCode.Include
	private UUID id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_ref")
    private String productRef;

    @Column(name = "quantity")
    private int quantity;

}
