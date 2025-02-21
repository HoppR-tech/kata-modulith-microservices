package tech.hoppr.model;

import lombok.Builder;
import tech.hoppr.shared.OrderId;

@Builder
public record CancelReservation(OrderId orderId) {
}
