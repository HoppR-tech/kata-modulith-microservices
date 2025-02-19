package tech.hoppr.modulith.order.model;

import lombok.Builder;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
