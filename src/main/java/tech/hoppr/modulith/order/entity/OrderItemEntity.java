package tech.hoppr.modulith.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@IdClass(OrderItemEntity.PK.class)
@Table(name = "order_items")
@Entity
@Builder
@NoArgsConstructor
@With
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "order")
public class OrderItemEntity {

    @Id
    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName="ID", insertable = false, updatable = false)
	@EqualsAndHashCode.Include
    private OrderEntity order;

    @Id
    @Column(name = "product_ref")
	@EqualsAndHashCode.Include
    private String productRef;

    @Column(name = "quantity")
    private int quantity;

    @RequiredArgsConstructor
	@EqualsAndHashCode
	@ToString
    @Getter
    public static class PK {
        private OrderEntity order;
        private String productRef;
    }

}
