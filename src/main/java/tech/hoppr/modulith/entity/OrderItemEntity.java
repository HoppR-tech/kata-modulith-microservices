package tech.hoppr.modulith.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@IdClass(OrderItemEntity.PK.class)
@Table(name = "ORDER_ITEM")
@Entity
@RequiredArgsConstructor
@Getter
public class OrderItemEntity {

    @Id
    @ManyToOne
    @JoinColumn(name="ORDER_ID", referencedColumnName="ID")
    private OrderEntity order;

    @Id
    @Column(name = "PRODUCT_REF")
    private UUID productRef;

    @Column(name = "QUANTITY")
    private int quantity;

    @RequiredArgsConstructor
    @Getter
    public static class PK {
        private OrderEntity order;
        private UUID productRef;
    }

}
