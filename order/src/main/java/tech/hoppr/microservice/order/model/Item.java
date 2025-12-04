package tech.hoppr.microservice.order.model;

import lombok.Builder;
import tech.hoppr.microservice.order.shared.ProductRef;
import tech.hoppr.microservice.order.shared.Quantity;

@Builder
public record Item(ProductRef product, Quantity quantity) {
}
