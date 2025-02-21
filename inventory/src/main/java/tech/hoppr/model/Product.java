package tech.hoppr.model;

import lombok.Builder;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

@Builder
public record Product(ProductRef ref, Quantity quantity) {
}
