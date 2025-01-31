package tech.hoppr.modulith.order.model;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderPlaced(OrderId orderId, List<Item> items) {
}
