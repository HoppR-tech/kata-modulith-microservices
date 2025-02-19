package tech.hoppr.modulith.inventory.model;

import lombok.Builder;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

@Builder
public record Product(ProductRef ref, Quantity quantity) {
}
