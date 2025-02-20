package tech.hoppr.modulith.inventory.model;

import lombok.Builder;
import tech.hoppr.modulith.order.model.OrderId;

@Builder
public record CancelReservation(OrderId orderId) {
}
