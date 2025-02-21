package tech.hoppr.order.model;

public sealed interface OrderEvent permits OrderCancelled, OrderPlaced {
	OrderId orderId();
}
