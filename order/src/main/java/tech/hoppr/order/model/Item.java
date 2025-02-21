package tech.hoppr.order.model;

import lombok.Builder;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
