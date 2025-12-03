package tech.hoppr.modulith.order.published;

import tech.hoppr.modulith.shared.OrderId;

public interface OrderEvent {
	OrderId orderId();
}
