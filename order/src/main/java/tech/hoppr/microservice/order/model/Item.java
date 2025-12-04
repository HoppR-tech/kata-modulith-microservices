package tech.hoppr.microservice.order.model;

import lombok.Builder;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
