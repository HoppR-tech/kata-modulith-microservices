package tech.hoppr.modulith.order.model;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, @Singular("item") List<Item> items) {
}
