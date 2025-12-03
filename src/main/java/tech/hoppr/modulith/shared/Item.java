package tech.hoppr.modulith.shared;

import lombok.Builder;
import tech.hoppr.modulith.inventory.model.ProductRef;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
