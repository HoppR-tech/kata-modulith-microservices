package tech.hoppr.modulith.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "INVENTORY")
@RequiredArgsConstructor
public class InventoryEntity {

    @Id
    @Column(name = "PRODUCT_REF")
    private UUID productRef;
    @Column(name = "QUANTITY")
    private int quantity;

}
