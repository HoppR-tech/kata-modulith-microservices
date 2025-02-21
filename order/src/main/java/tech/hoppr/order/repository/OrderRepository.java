package tech.hoppr.order.repository;

import tech.hoppr.order.model.OrderId;
import tech.hoppr.order.model.Order;

public interface OrderRepository {

	Order getBy(OrderId orderId);

    void save(Order order);
}
