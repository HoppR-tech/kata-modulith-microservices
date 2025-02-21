package tech.hoppr.inventory.model;

import lombok.Builder;

@Builder
public record CancelReservation(OrderId orderId) {
}
