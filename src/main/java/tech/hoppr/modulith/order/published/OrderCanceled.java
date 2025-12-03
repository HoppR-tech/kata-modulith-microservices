package tech.hoppr.modulith.order.published;

import lombok.Builder;
import tech.hoppr.modulith.shared.OrderId;

@Builder
public record OrderCanceled(OrderId orderId) {
}
