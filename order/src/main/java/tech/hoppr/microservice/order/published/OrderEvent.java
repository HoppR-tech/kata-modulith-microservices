package tech.hoppr.microservice.order.published;

import tech.hoppr.microservice.order.model.OrderId;

public interface OrderEvent {
	OrderId orderId();
}
