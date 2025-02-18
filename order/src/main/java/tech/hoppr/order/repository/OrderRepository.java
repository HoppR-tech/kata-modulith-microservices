package tech.hoppr.order.repository;

import tech.hoppr.order.model.Order;
import tech.hoppr.order.model.OrderId;

public interface OrderRepository {

	Order getBy(OrderId orderId);

    void save(Order order);
}
