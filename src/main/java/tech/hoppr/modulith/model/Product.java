package tech.hoppr.modulith.model;

import lombok.Builder;

@Builder
public record Product(ProductRef ref, Quantity quantity) {
}
