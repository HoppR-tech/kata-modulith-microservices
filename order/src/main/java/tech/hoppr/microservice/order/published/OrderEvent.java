package tech.hoppr.microservice.order.published;

import tech.hoppr.microservice.order.shared.OrderId;

public interface OrderEvent {
	OrderId orderId();
}
