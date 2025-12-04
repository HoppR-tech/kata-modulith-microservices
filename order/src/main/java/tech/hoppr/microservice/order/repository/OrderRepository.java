package tech.hoppr.microservice.order.repository;

import tech.hoppr.microservice.order.model.Order;
import tech.hoppr.microservice.order.model.OrderId;

public interface OrderRepository {

	Order getBy(OrderId orderId);

    void save(Order order);

	void remove(OrderId orderId);
}
