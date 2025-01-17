package tech.hoppr.modulith.model;

import lombok.Builder;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
