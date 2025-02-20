package tech.hoppr.modulith.shared;

import lombok.Builder;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
