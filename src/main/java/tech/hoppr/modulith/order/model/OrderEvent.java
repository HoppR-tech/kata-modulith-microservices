package tech.hoppr.modulith.order.model;

public sealed interface OrderEvent permits OrderCancelled, OrderPlaced {
	OrderId orderId();
}
